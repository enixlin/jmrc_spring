package com.enixlin.jmrc.policydocument;

public class PolicyDocument {

	public static void main(String[] args) {
		// String filePath = "d:/libs_types.law";
		String filePath = "d:/201809.law";

		DataFileAccess dfa = new DataFileAccess();
		dfa.fileToObject(filePath);

		dfa.saveObjectToDB("localhost", "3306", "jmrc", "linzhenhuan", "enixlin1981");
		// dfa.createDBTable("localhost", "3306", "jmrc", "linzhenhuan", "enixlin1981");

		System.out.println("done");
	}

}
