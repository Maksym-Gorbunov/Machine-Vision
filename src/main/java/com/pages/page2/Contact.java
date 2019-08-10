package com.pages.page2;

import org.bson.types.ObjectId;

public class Contact {
  private static final long serialVersionUID = 1L;
  private ObjectId _id = new ObjectId();
  private String name;
  private String surname;
  private String email;
  private String phone;

  public Contact() {
    name = "";
    surname = "";
    email = "";
    phone = "";
  }

  public Contact(String name, String surname, String email, String phone) {
    this.name = name;
    this.surname = surname;
    this.email = email;
    this.phone = phone;
  }

  @Override
  public String toString() {
//    return "Contact{" +
//            "name='" + name + '\'' +
//            ", surname='" + surname + '\'' +
//            ", email='" + email + '\'' +
//            ", phone='" + phone + '\'' +
//            '}';
    return name + " " + surname + ", mail: " + email + ", phone: " + phone;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Contact contact = (Contact) o;

    if ((email == null) || (phone == null) || (!email.equals(contact.email))) return false;
    return phone.equals(contact.phone);
  }

  @Override
  public int hashCode() {
    int result = email.hashCode();
    result = 31 * result + phone.hashCode();
    return result;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public ObjectId get_id() {
    return _id;
  }
}
