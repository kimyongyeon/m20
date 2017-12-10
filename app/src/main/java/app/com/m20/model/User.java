package app.com.m20.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by kimyongyeon on 2017-12-06.
 */

public class User extends RealmObject {

    private int rNumber; // 예약번호
    private String sel; // 체지방 진행 YES / NO ==== 0, 1

    // 마이프로그램
    private RealmList<Body> program; // 총 5개 생성.
    private String name; // 이름
    private String height; // 키
    private String weight; // 몸무게
    private String age; // 나이

    public int getrNumber() {
        return rNumber;
    }

    public void setrNumber(int rNumber) {
        this.rNumber = rNumber;
    }

    public String getSel() {
        return sel;
    }

    public void setSel(String sel) {
        this.sel = sel;
    }

    public RealmList<Body> getProgram() {
        return program;
    }

    public void setProgram(RealmList<Body> program) {
        this.program = program;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
