package com.blake.util.share.db.dao;

import java.util.List;

public interface IInfoDao {
	
    void insert(Info user);  
      
    void insertAll(List<Info> users);  
      
    void deleteById(String id);  

    void delete(Info criteriaInfo);  

    boolean deleteAll();  

    void updateById(Info user);  

    void update(Info criteriaInfo, Info user);  
      
    Info findById(String id);  

    List<Info> findAll();  
      
    List<Info> find(String key, int skip, int limit);  

    Info findAndModify(Info criteriaInfo, Info updateInfo);  

    Info findAndRemove(Info criteriaInfo);  

    long count(Info criteriaInfo);  
    
    void createIndex(String key);  
    
    boolean hasIndex();

}
