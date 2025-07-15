package com.railway.dao;

import com.railway.model.Admin;

public interface AdminDAO {
    boolean registerAdmin(Admin admin);
    Admin login(String username, String password);
    boolean updatePassword(int adminId, String newPassword);
    boolean deleteAdmin(int adminId);
}
