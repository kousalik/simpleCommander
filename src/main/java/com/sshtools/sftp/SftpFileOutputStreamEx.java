package com.sshtools.sftp;

import com.sshtools.ssh.SshException;

/**
 * Modified SftpFileOutputStream with position argument in constructor
 *
 */
public class SftpFileOutputStreamEx extends SftpFileOutputStream {

    protected SftpFileOutputStreamEx(SftpFile file, long pos) throws SftpStatusException, SshException {
        super(file);
        this.position = pos;
    }
}
