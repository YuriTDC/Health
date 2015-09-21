package io.redspark.email.overview.controller;

import java.io.IOException;

import org.eclipse.jgit.api.BlameCommand;
import org.eclipse.jgit.api.errors.GitAPIException;

public class ShowLog {

	@SuppressWarnings({ "resource" })
	public static void main(String[] args) throws IOException, GitAPIException {
		
/*		Repository repository = CookbookHelper.openJGitCookbookRepository();

		Iterable<RevCommit> logs = new Git(repository).log().call();
		int count = 0;
		for (RevCommit rev : logs) {
			//System.out.println("Commit: " + rev  + ", name: " + rev.getName() + ", id: " + rev.getId().getName() );
			count++;
		}
		System.out.println("Had " + count + " commits overall on current branch");*/
		
		ShowLog.readLine();
	}
	
	public static void readLine() {

			
	}
	
	
}