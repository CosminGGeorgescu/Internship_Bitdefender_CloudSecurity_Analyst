package Detector;

import org.json.JSONObject;

public class Alert {
    public String CisRuleId;
    public String CisRuleDescription;
    public JSONObject ExtraInfo;

    public Alert(String cisRuleId, String cisRuleDescription, JSONObject extraInfo) {
        CisRuleId = cisRuleId;
        CisRuleDescription = cisRuleDescription;
        ExtraInfo = extraInfo;
    }

    public Alert(String cisRuleId, String cisRuleDescription) {
        CisRuleId = cisRuleId;
        CisRuleDescription = cisRuleDescription;
        ExtraInfo = new JSONObject();
    }

    public String getCisRuleId() {
        return CisRuleId;
    }

    public void setCisRuleId(String cisRuleId) {
        CisRuleId = cisRuleId;
    }

    public void addExtraInfo(String extraInfoKey, Object extraInfoValue)
    {
        ExtraInfo.put(extraInfoKey, extraInfoValue);
    }

    public String getCisRuleDescription() {
        return CisRuleDescription;
    }

    public void setCisRuleDescription(String cisRuleDescription) {
        CisRuleDescription = cisRuleDescription;
    }

    public JSONObject getExtraInfo() {
        return ExtraInfo;
    }

    public void setExtraInfo(JSONObject extraInfo) {
        ExtraInfo = extraInfo;
    }

    @Override
    public String toString() {
        return "Alert{" +
                "CisRuleId='" + CisRuleId + '\'' +
                ", CisRuleDescription='" + CisRuleDescription + '\'' +
                ", ExtraInfo=" + ExtraInfo.toString() +
                '}';
    }
}
