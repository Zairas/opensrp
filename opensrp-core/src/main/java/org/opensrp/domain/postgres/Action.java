package org.opensrp.domain.postgres;

public class Action {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column core.action.id
     *
     * @mbg.generated Fri Mar 09 15:24:01 EAT 2018
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column core.action.json
     *
     * @mbg.generated Fri Mar 09 15:24:01 EAT 2018
     */
    private Object json;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column core.action.id
     *
     * @return the value of core.action.id
     *
     * @mbg.generated Fri Mar 09 15:24:01 EAT 2018
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column core.action.id
     *
     * @param id the value for core.action.id
     *
     * @mbg.generated Fri Mar 09 15:24:01 EAT 2018
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column core.action.json
     *
     * @return the value of core.action.json
     *
     * @mbg.generated Fri Mar 09 15:24:01 EAT 2018
     */
    public Object getJson() {
        return json;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column core.action.json
     *
     * @param json the value for core.action.json
     *
     * @mbg.generated Fri Mar 09 15:24:01 EAT 2018
     */
    public void setJson(Object json) {
        this.json = json;
    }
}