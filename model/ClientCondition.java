package model;


public class ClientCondition {
    private int conditionID;
    private String conditionDesc;

    public ClientCondition () {}

    public ClientCondition(int cid, String cd) {
        this.conditionID = cid;
        this.conditionDesc = cd;
    }

    public ClientCondition(int cid) {
        this.conditionID = cid;
    }

    public int getConditionID() {
        return conditionID;
    }

    public void setConditionID(int cd) {
        this.conditionID = cd;
    }

    public String getConditionDesc() {
        return conditionDesc;
    }

    public void setConditionDesc(String cd) {
        this.conditionDesc = cd;
    }
    
    public boolean equals(Object obj) {
    	if (obj == null) return false;
    	if (obj == this) return true;
    	if (!(obj instanceof ClientCondition)) return false;
    	ClientCondition o = (ClientCondition) obj;
    	return o.getConditionID() == this.conditionID;
    }

	@Override
	public String toString() {
		return "ClientCondition [conditionID=" + conditionID + ", conditionDesc=" + conditionDesc + "]";
	}

}
