package com.nagarro.model;

import java.util.List;

import com.nagarro.entity.User;


public  class UsersResponse {
    private List<User> users;
    private PageInfo pageInfo;

    public UsersResponse(List<User> users, PageInfo pageInfo) {
        this.users = users;
        this.pageInfo = pageInfo;
    }

    public List<User> getUsers() {
        return users;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

	
	
    
}

