package Detector.ExamplePlatform.Pojos;

public class ServerCertificate {

    private String Path;
    private String ServerCertificateName;
    private String ServerCertificateId;
    private String Arn;
    private String UploadDate;
    private String Expiration;

    public ServerCertificate(String path, String serverCertificateName, String serverCertificateId, String arn, String uploadDate, String expiration) {
        Path = path;
        ServerCertificateName = serverCertificateName;
        ServerCertificateId = serverCertificateId;
        Arn = arn;
        UploadDate = uploadDate;
        Expiration = expiration;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }

    public String getServerCertificateName() {
        return ServerCertificateName;
    }

    public void setServerCertificateName(String serverCertificateName) {
        ServerCertificateName = serverCertificateName;
    }

    public String getServerCertificateId() {
        return ServerCertificateId;
    }

    public void setServerCertificateId(String serverCertificateId) {
        ServerCertificateId = serverCertificateId;
    }

    public String getArn() {
        return Arn;
    }

    public void setArn(String arn) {
        Arn = arn;
    }

    public String getUploadDate() {
        return UploadDate;
    }

    public void setUploadDate(String uploadDate) {
        UploadDate = uploadDate;
    }

    public String getExpiration() {
        return Expiration;
    }

    public void setExpiration(String expiration) {
        Expiration = expiration;
    }

    @Override
    public String toString() {
        return "ServerCertificate{" +
                "Path='" + Path + '\'' +
                ", ServerCertificateName='" + ServerCertificateName + '\'' +
                ", ServerCertificateId='" + ServerCertificateId + '\'' +
                ", Arn='" + Arn + '\'' +
                ", UploadDate='" + UploadDate + '\'' +
                ", Expiration='" + Expiration + '\'' +
                '}';
    }
}
