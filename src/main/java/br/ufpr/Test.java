package br.ufpr;

import jakarta.persistence.Persistence;

public class Test {

  public static void main(String[] args) {
    var emf = Persistence.createEntityManagerFactory("persistence");
    System.out.println("EntityManagerFactory created: " + emf);
    emf.close();
  }

}
