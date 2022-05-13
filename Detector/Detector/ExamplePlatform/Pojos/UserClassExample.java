package Detector.ExamplePlatform.Pojos;

// Java class for the python generated [.list_users()] JSON
public class UserClassExample {
    String Path;
    String UserName;
    String UserId;
    String Arn;
    String CreateDate;

    @Override
    public String toString() {
        return "UserClassExample{" +
                "Path='" + Path + '\'' +
                ", UserName='" + UserName + '\'' +
                ", UserId='" + UserId + '\'' +
                ", Arn='" + Arn + '\'' +
                ", CreateDate='" + CreateDate + '\'' +
                '}';
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getArn() {
        return Arn;
    }

    public void setArn(String arn) {
        Arn = arn;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }
}
