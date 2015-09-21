package test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.egit.github.core.service.RepositoryService;
import org.eclipse.jgit.api.Git;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.springframework.beans.factory.annotation.Autowired;

public class Main {

     private static final String url = "https://github.com/dclick/email-overview";
     
     @Autowired
     private static RepositoryService repositoryService;
     
        public static void main(String[] args) throws IOException, InvalidRemoteException, TransportException, GitAPIException {
            File path = File.createTempFile("TestGitRepository", "");
            path.delete();
            
            List<Repository> repositories = repositoryService.getRepositories();

            System.out.println("Clonando " + url + " to " + path);
            Git.cloneRepository().setURI(url).setDirectory(path).call();

            Git git = Git.open( path );
            org.eclipse.jgit.lib.Repository repository = git.getRepository();

            Status status = new Git(repository).status().call();
            
            System.out.println("Adicionado: " + status.getAdded());
            System.out.println("Alterado: " + status.getChanged());
            System.out.println("Conflito: " + status.getConflicting());
            System.out.println("Faltando: " + status.getMissing());
            System.out.println("Modificado: " + status.getModified());
            System.out.println("Removido: " + status.getRemoved());

            repository.close();
        }
}