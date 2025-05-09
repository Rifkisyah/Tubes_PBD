package com.bookstore.data;

/**
 *
 * @author rifki
 */
public class EmployeeAccount {
    private String id, roleId, name, password;

    public EmployeeAccount(String id, String roleId, String name, String password){
        this.id = id;
        this.roleId = roleId;
        this.name = name;
        this.password = password;
    }
    
    public EmployeeAccount(){
        
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getRoleId() {
        return roleId;
    }
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
