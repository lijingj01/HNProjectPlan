package com.hn.business.Data;

public class UserInfoEntity {
    //region 内部属性
    private int Id;
    private String UserCode;
    private String UserName;
    private String NickName;

    public UserInfoEntity() {

    }

    public UserInfoEntity(int Id, String strUserCode, String strUserName, String strNickName) {
        this.Id = Id;
        this.UserCode = strUserCode;
        this.UserName = strUserName;
        this.NickName = strNickName;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String userCode) {
        UserCode = userCode;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }
    //endregion

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }
}
