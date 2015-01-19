package com.icebreak.p2p.dal.dataobject;

/**
*
* @create by CodeMaker
* @Description 自动生成DO
* @Version 1.0
* @Author xy
* @Email xy@taogushen.com
* @History
*<li>Author: xy</li>
*<li>Date: </li>
*<li>Version: 1.0</li>
*<li>Content: create</li>
*
*/
public abstract class ExtendAttrDOAbstract {

	public static final String EXTENDATTRDO = "EXTENDATTRDO";
    /***/
    private java.lang.Long tblBaseId;
    /**记录编号*/
    private java.lang.Long recordId;
    /**属性名*/
    private String attrName;
    /**属性值*/
    private String attrValue;
	
    /**
     *	获取
     *	@return tblBaseId 
     */
    public java.lang.Long getTblBaseId (){
        return this.tblBaseId;
	}
		
    /**
     *	设置
     *	@param tblBaseId 
     */
    public void setTblBaseId (java.lang.Long tblBaseId){
        this.tblBaseId = tblBaseId;
    }
    /**
     *	获取记录编号
     *	@return recordId 记录编号
     */
    public java.lang.Long getRecordId (){
        return this.recordId;
	}
		
    /**
     *	设置记录编号
     *	@param recordId 记录编号
     */
    public void setRecordId (java.lang.Long recordId){
        this.recordId = recordId;
    }
    /**
     *	获取属性名
     *	@return attrName 属性名
     */
    public String getAttrName (){
        return this.attrName;
	}
		
    /**
     *	设置属性名
     *	@param attrName 属性名
     */
    public void setAttrName (String attrName){
        this.attrName = attrName;
    }
    /**
     *	获取属性值
     *	@return attrValue 属性值
     */
    public String getAttrValue (){
        return this.attrValue;
	}
		
    /**
     *	设置属性值
     *	@param attrValue 属性值
     */
    public void setAttrValue (String attrValue){
        this.attrValue = attrValue;
    }
	
}