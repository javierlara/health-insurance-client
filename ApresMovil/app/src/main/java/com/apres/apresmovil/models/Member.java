package com.apres.apresmovil.models;

/**
 * Created by javierlara on 3/5/17.
 */

public class Member {
    public final String id;
    public final String address;
    public final Integer member_number;
    public final String name;
    public final Integer plan_id;
    public final String telephone;

    public Member(String id, String address, Integer member_number, String name, Integer plan_id, String telephone) {
        this.id = id;
        this.address = address;
        this.member_number = member_number;
        this.name = name;
        this.plan_id = plan_id;
        this.telephone = telephone;
    }
}
