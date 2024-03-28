package com.nagarro.model;


public class PageInfo {
	private int offset;
    private int limit;
	private boolean hasNextPage;
	private boolean hasPreviousPage;
    private long total;
    
	public PageInfo(int offset, int limit, boolean hasNextPage, boolean hasPreviousPage, long total) {
		
		this.offset = offset;
		this.limit = limit;
		this.hasNextPage = hasNextPage;
		this.hasPreviousPage = hasPreviousPage;
		this.total = total;
	}
	
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public boolean isHasNextPage() {
		return hasNextPage;
	}
	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}
	public boolean isHasPreviousPage() {
		return hasPreviousPage;
	}
	public void setHasPreviousPage(boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}

	
    
   
	
}
