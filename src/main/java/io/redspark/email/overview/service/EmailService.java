package io.redspark.email.overview.service;

import io.redspark.email.overview.entity.Integrante;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.eclipse.egit.github.core.Issue;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.util.ResourceUtils;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private Environment env;

	private static final Logger log = LoggerFactory.getLogger(EmailService.class);

	public void enviarEmailSimples(String reciever, String texto) throws Exception {

		SimpleMailMessage sm = factorySimpleEmail(texto, reciever);

		mailSender.send(sm);
	}

	public void enviarEmail(String reciever, String texto, String projeto, Set<Integrante> time) throws Exception {

		MimeMessage message = factoryEmail(reciever, texto, projeto, time);

		boolean success = send(message);

		if (success) {
			log.info("Email overview enviado com sucesso para : " + reciever);
		}
	}

	private boolean send(MimeMessage message) throws Exception {

		boolean success = false;

		try {

			mailSender.send(message);
			success = true;

		} catch (RuntimeException e) {
			log.info("Falha no envio do email-overview: {}", message.getSubject());
			e.printStackTrace();
			success = false;
		}

		return success;
	}

	private MimeMessage factoryEmail(String reciever, String texto, String projeto, Set<Integrante> time) throws MessagingException, IOException {

		String from = "overview@redspark.io";
		String to = reciever;
		String subject = "redspark overview: " + projeto + " - " + LocalDateTime.now().toString("dd/MM/YYYY");
		String text = texto;

		MimeMessage message = mailSender.createMimeMessage();

		message.setFrom(new InternetAddress(from));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		message.setSubject(subject);

		MimeMultipart multipart = new MimeMultipart("related");
		BodyPart messageBodyPart = new MimeBodyPart();
		String htmlText = text;
		messageBodyPart.setContent(htmlText, "text/html;charset=UTF-8");

		multipart.addBodyPart(messageBodyPart);

		messageBodyPart = new MimeBodyPart();

		File file = ResourceUtils.getFile(env.getRequiredProperty("path.to.logo"));
		DataSource fds = new FileDataSource(file);
		messageBodyPart.setDataHandler(new DataHandler(fds));
		messageBodyPart.setHeader("Content-ID", "<image>");
		messageBodyPart.setDisposition("inline");
		multipart.addBodyPart(messageBodyPart);

		int c = 0;
		for (Integrante i : time) {

			if (!i.getAvatar().isEmpty()) {

				messageBodyPart = new MimeBodyPart();
				fds = new FileDataSource(i.getAvatar());
				messageBodyPart.setDataHandler(new DataHandler(fds));
				messageBodyPart.setHeader("Content-ID", "<image_" + c + ">");
				messageBodyPart.setDisposition("inline");
				multipart.addBodyPart(messageBodyPart);
				c++;
			}
		}

		message.setContent(multipart, "text/html; charset=UTF-8");

		return message;
	}

	public String factoryEmail(String projeto, Integer bugsAtualmente, Integer backlogAtualmente, Integer inProgressAtualmente, Integer closedAtualmente, Integer addedSinceOntem,
			Integer bugsSinceOntem, Integer inProgressSinceOntem, Integer closedSinceOntem, List<Issue> tarefasFinalizadas, Set<Integrante> time) throws FileNotFoundException, IllegalStateException {

		Map<String, String> map = new LinkedHashMap<String, String>();
		List<Map<String, String>> tarefasFinalizadasMapeadas = new ArrayList<Map<String, String>>();
		List<Map<String, String>> timeMapeado = new ArrayList<Map<String, String>>();

		geraListaIssuesFinalizadas(map, tarefasFinalizadasMapeadas, tarefasFinalizadas);
		geraListaTime(map, timeMapeado, time);

		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		ve.init();

		Map<String, Object> context = new HashMap<String, Object>();

		context.put("projeto", projeto);
		context.put("dataAtual", LocalDateTime.now().toString("dd/MM/YYYY"));
		context.put("bugsAtualmente", bugsAtualmente);
		context.put("backlogAtualmente", backlogAtualmente);
		context.put("inProgressAtualmente", inProgressAtualmente);
		context.put("closedAtualmente", closedAtualmente);
		context.put("bugsSinceOntem", bugsSinceOntem);
		context.put("addedSinceOntem", addedSinceOntem);
		context.put("resolvedSinceOntem", closedSinceOntem);
		context.put("inProgressSinceOntem", inProgressSinceOntem);
		context.put("tarefasFinalizadas", tarefasFinalizadasMapeadas);
		context.put("time", timeMapeado);

		String template = VelocityEngineUtils.mergeTemplateIntoString(ve, "templates/email_overview.vm", "UTF-8", context);

		// System.out.println(template);

		return template;
	}

	private static void geraListaIssuesFinalizadas(Map<String, String> m, List<Map<String, String>> tarefas, List<Issue> issues) {

		Collections.sort(issues, COMPARAR_POR_DATA);

		for (Issue issue : issues) {

			LocalDateTime data = new LocalDateTime(issue.getClosedAt());

			m = new LinkedHashMap<String, String>();

			m.put("title", issue.getTitle());
			m.put("data", data.toString("dd/MM/YYYY"));
			tarefas.add(m);
		}
	}

	private static void geraListaTime(Map<String, String> m, List<Map<String, String>> contribuidores, Set<Integrante> time) {

		int c = 0;
		for (Integrante i : time) {

			m = new LinkedHashMap<String, String>();
			m.put("nome", i.getNome());
			m.put("email", i.getEmail());

			if (!i.getAvatar().isEmpty()) {
				m.put("avatar", "image_" + c);
				c++;
			} else {
				m.put("avatar", "");
			}

			contribuidores.add(m);
		}
	}

	public static Comparator<Issue> COMPARAR_POR_DATA = new Comparator<Issue>() {
		public int compare(Issue issue, Issue issue2) {
			int is = -1;

			if (new LocalDateTime(issue.getClosedAt()).isAfter(new LocalDateTime(issue.getClosedAt()))) {
				is = 1;
			}

			return is;
		}
	};

	private SimpleMailMessage factorySimpleEmail(String texto, String reciever) {

		String from = "overview@redspark.io";
		String to = reciever;
		String subject = "Teste de email";

		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(texto);

		return message;
	}
}
