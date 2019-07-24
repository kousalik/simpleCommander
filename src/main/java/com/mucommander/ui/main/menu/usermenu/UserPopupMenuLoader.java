package com.mucommander.ui.main.menu.usermenu;

import com.mucommander.commons.file.AbstractFile;
import com.mucommander.ui.helper.MnemonicHelper;
import com.mucommander.ui.main.MainFrame;
import com.mucommander.ui.main.menu.UserPopupMenu;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import ru.trolsoft.ui.TMenuSeparator;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class UserPopupMenuLoader {

    public static UserPopupMenu loadMenu(MainFrame mainFrame, AbstractFile file) throws IOException, LoadUserMenuException {
        try (InputStream is = file.getInputStream()) {
            JSONObject root = new JSONObject(new JSONTokener(is));
            JSONArray items = root.getJSONArray("menu");
            MnemonicHelper mnemonicHelper = new MnemonicHelper();
            UserPopupMenu menu = new UserPopupMenu(mainFrame, file);
            loadMenu(menu, null, items, mnemonicHelper);
            return menu;
        } catch (JSONException e) {
            throw new LoadUserMenuException(e);
        }
    }

    private static void loadMenu(UserPopupMenu menu, JMenu parent, JSONArray items, MnemonicHelper mnemonicHelper) {
        if (items == null) {
            return;
        }
        for (int i = 0; i < items.length(); i++) {
            Object obj = items.get(i);
            if (obj instanceof String) {
                menu.add(new TMenuSeparator());
            } else if (obj instanceof JSONObject) {
                JSONObject item = items.getJSONObject(i);
                String type = getItemProp(item, "type");

                if ("separator".equalsIgnoreCase(type)) {
                    menu.add(new TMenuSeparator());
                } else if (type == null || "item".equalsIgnoreCase(type)) {
                    String name = getItemProp(item,"name");

                    UserMenuItem.Command command = getItemCommand(item);
                    String console = getItemProp(item, "console");
                    String key = getItemProp(item,"key");
                    UserMenuItem properties = new UserMenuItem(
                            command,
                            UserMenuItem.ConsoleType.fromStr(console));
                    JMenuItem mi = menu.add(parent, name, properties);
                    mi.setMnemonic(mnemonicHelper.getMnemonic(name));
                    if (key != null) {
                        KeyStroke keyStroke = KeyStroke.getKeyStroke(key);
                        mi.setAccelerator(keyStroke);
                    }
                } else if ("menu".equalsIgnoreCase(type)) {
                    String name = getItemProp(item,"name");
                    JMenu submenu = new JMenu(name);
                    submenu.setMnemonic(mnemonicHelper.getMnemonic(name));
                    if (parent == null) {
                        menu.add(submenu);
                    } else {
                        parent.add(submenu);
                    }
                    loadMenu(menu, submenu, item.getJSONArray("items"), new MnemonicHelper());
                }
            } else {
                throw new JSONException("Not JSON object found at index " + i);
            }
        }
    }

    private static UserMenuItem.Command getItemCommand(JSONObject item) {
        if (!item.has("command")) {
            return null;
        }
        Object cmd = item.get("command");
        if (cmd instanceof String) {
            return new UserMenuItem.Command((String) cmd);
        } else if (cmd instanceof JSONArray) {
            JSONArray array = (JSONArray) cmd;
            List<List<String>> result = new ArrayList<>();
            if (arrayContainsArrays(array)) {
                for (Object o : array) {
                    List<String> group = new ArrayList<>();
                    result.add(group);
                    if (o instanceof JSONArray) {
                        JSONArray groupArray = (JSONArray) o;
                        for (Object c : groupArray) {
                            if (c instanceof String) {
                                group.add((String) c);
                            } else {
                                throw new JSONException("invalid command type " + item);
                            }
                        }
                    }
                }
                return new UserMenuItem.Command(result);
            } else {
                List<String> group = new ArrayList<>();
                result.add(group);
                for (Object c : array) {
                    if (c instanceof String) {
                        group.add((String) c);
                    } else {
                        throw new JSONException("invalid command type " + item);
                    }
                }
                return new UserMenuItem.Command(result);
            }

        }
        throw new JSONException("invalid command type " + item);
    }

    private static boolean arrayContainsArrays(JSONArray array) {
        for (Object o : array) {
            if (o instanceof JSONArray) {
                return true;
            }
        }
        return false;
    }


    @Nullable
    private static String getItemProp(JSONObject item, String name) {
        return item.has(name) ? item.getString(name) : null;
    }
}
