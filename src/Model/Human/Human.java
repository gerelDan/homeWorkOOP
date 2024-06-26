package Model.Human;

import Model.FamilyTree.LeafFamilyTree;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Human implements Serializable, LeafFamilyTree {
    private long id = -1;
    private final String firstName;
    private final String lastName;
    private Human mother;
    private Human father;
    private List<Human> childrens;
    private final Gender gender;
    private final LocalDate dob;
    private LocalDate dod;
    private Human spouse;

    public Human(String firstName, String lastName, Gender gender, LocalDate dob, LocalDate dod, Human mother, Human father){
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dob = dob;
        this.dod = dod;
        this.mother = mother;
        this.father = father;
        this.childrens = new LinkedList<Human>();
    }

    public Human(String firstName, String lastName, Gender gender, LocalDate dob) {
        this(firstName, lastName, gender, dob, null, null, null);
    }
    public Human(String firstName, String lastName, Gender gender, LocalDate dob, LocalDate dod){
        this(firstName, lastName, gender, dob, dod, null, null);
    }

    public Human(String firstName, String lastName, Gender gender, LocalDate dob, Human mother, Human father){
        this(firstName, lastName, gender, dob, null, mother, father);
    }
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Human getMother() {
        return mother;
    }

    public Human getFather() {
        return father;
    }

    public List<Human> getChildren() {
        return childrens;
    }

    public LocalDate getDob() {
        return dob;
    }

    public LocalDate getDod() {
        return dod;
    }

    public long getId(){
        return this.id;
    }
    public void setId(long id){
        this.id = id;
    }

    public boolean setChildren(Human children) {
        if (!childrens.contains(children)) {
            this.childrens.add(children);
            if (this.gender.equals(Gender.male) && children.father == null) {
                children.father = this;
            } else if (this.gender.equals(Gender.female) && children.mother == null) {
                children.mother = this;
            }
        return true;
        }
        else return false;
    }


    public void setDod(LocalDate dod) {
        this.dod = dod;
    }

    public boolean addParent(Human parent) {
        if (this.getMother() == null && parent.gender.equals(Gender.female)) {
            this.mother = parent;
            boolean x = parent.setChildren(this);
            return true;
        }
        else if (this.getFather() == null && parent.gender.equals(Gender.male)) {
            this.father = parent;
            boolean x = parent.setChildren(this);
            return true;
        }
        else return false;
    }

    public List<Human> getParents(){
        List<Human> list = new LinkedList<Human>();
        if (this.mother != null){
            list.add(this.mother);
        }
        if (this.father != null){
            list.add(this.father);
        }
        return list;
    }
    public Long getAge(){
        if (this.dod == null){
            return ChronoUnit.YEARS.between(this.dob, LocalDate.now());
        }
        else{
            return ChronoUnit.YEARS.between(this.dob, this.dod);
        }
    }


    @Override
    public String toString() {
        return this.getInfo();
    }

    public String getStatus(){
        if (dod == null){
            return "alive";
        }else return "dead";
    }

    public String getChildrenInfo(){
        StringBuilder stringBuilder = new StringBuilder();
        if (this.childrens.isEmpty())  stringBuilder.append("N/D");
        else {
            stringBuilder.append(this.childrens.get(0).getFirstName());
            for (int i = 1; i < this.childrens.size(); i++){
                stringBuilder.append(", ");
                stringBuilder.append(this.childrens.get(i).getFirstName());
            }
        }
        return stringBuilder.toString();
    }

    public List<String> getGrandParents(){
        List<String> list = new LinkedList<String>();
        if (this.mother != null && !this.mother.getParents().isEmpty()){
            for (Human human : this.mother.getParents()){
                list.add(human.getFirstName());
            }
        }
        if (this.father != null && !this.father.getParents().isEmpty()){
            for (Human human : this.father.getParents()){
                list.add(human.getFirstName());
            }

        }
        return list;
    }


    public String getInfo(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ID: ");
        stringBuilder.append(id);
        stringBuilder.append(", Name: ");
        stringBuilder.append(firstName);
        stringBuilder.append(", Last Name: ");
        stringBuilder.append(lastName);
        stringBuilder.append(", Age: ");
        stringBuilder.append(this.getAge());
        stringBuilder.append(", Father: ");
        if (father != null) stringBuilder.append(father.firstName);
        else stringBuilder.append("N/D");
        stringBuilder.append(", Mother: ");
        if (mother != null) stringBuilder.append(mother.firstName);
        else stringBuilder.append("N/D");
        stringBuilder.append(", Spouse: ");
        if (spouse != null) stringBuilder.append(spouse.firstName);
        else stringBuilder.append("N/D");
        stringBuilder.append(", Children: ");
        stringBuilder.append(this.getChildrenInfo());
        stringBuilder.append(", Status: ");
        stringBuilder.append(this.getStatus());

        return stringBuilder.toString();
    }

    @Override
    public void setSpouse(Object human) {
        this.spouse = (Human) human;
    }


//    public boolean setSpouse(Human spouse) {
//        if (spouse.getSpouse() == null && this.getSpouse() == null){
//            this.spouse = spouse;
//            return true;
//        }
//        else return false;
//
//    }

    public Human getSpouse() {
        return spouse;
    }
}