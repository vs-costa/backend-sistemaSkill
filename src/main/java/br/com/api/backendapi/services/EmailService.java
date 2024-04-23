package br.com.api.backendapi.services;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import java.time.LocalDate;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import br.com.api.backendapi.dto.SystemUserDTO;

@Configuration
@Service
public class EmailService {

	@Autowired
	SystemUserService userService;

	private JavaMailSender emailSender;

	// no email o @Autowired é feito no construtor por causa da dependência
	@Autowired
	public void setJavaMailSender(JavaMailSender emailSender) {
		this.emailSender = emailSender;
	}

	@Value("${spring.mail.host}")
	private String host;

	@Value("${spring.mail.port}")
	private Integer port;

	@Value("${spring.mail.username}")
	private String username;

	@Value("${spring.mail.password}")
	private String password;

	@Bean
	public JavaMailSender javaMailSender() {
		JavaMailSenderImpl emailSender = new JavaMailSenderImpl();
		Properties prop = new Properties();
		emailSender.setHost(host);
		emailSender.setPort(port);
		emailSender.setUsername(username);
		emailSender.setPassword(password);
		prop.put("mail.transport.protocol", "smtp");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		emailSender.setJavaMailProperties(prop);
		return emailSender;
	}

	public void envioEmailCadastro(SystemUserDTO user) {
		MimeMessage mensagemCadastro = emailSender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(mensagemCadastro, true);
			helper.setFrom("victorsoares.c@gmail.com");
			helper.setTo(user.getEmail());
			helper.setSubject("Cadastro concluido!");

			LocalDate localDate = LocalDate.now();

			StringBuilder builder = new StringBuilder();
			builder.append("<html>\r\n");
			builder.append("	<body>\r\n");
			builder.append("		<div align=\"center\">\r\n");
			builder.append("			<h1>Confirmação de Cadastro</h1>\r\n");
			builder.append("		</div>\r\n");
			builder.append("		<br/>\r\n");
			builder.append("		<div align=\"left\">\r\n");
			builder.append("			Olá,\r\n");
			builder.append("		</div>\r\n");
			builder.append("		<div align=\"left\">\r\n");
			builder.append("			<br/>\r\n");
			builder.append("			Bem vindo ao sistema de skills.\r\n");
			builder.append("		</div>\r\n");
			builder.append("		<div align=\"left\">\r\n");
			builder.append("			<br/>\r\n");
			builder.append("			Aqui estão os detalhes do seu cadastro:\r\n");
			builder.append("		</div>\r\n");
			builder.append("		<div align=\"left\">\r\n");
			builder.append("			Endereço de E-mail: \r\n");
			builder.append(user.getEmail());
			builder.append("		</div>\r\n");
			builder.append("		<div align=\"left\">\r\n");
			builder.append("			Data de Cadastro: \r\n");
			builder.append(localDate);
			builder.append("		</div>\r\n");
			builder.append("		<div align=\"left\">\r\n");
			builder.append("			<br/>\r\n");
			builder.append(
					"			Agora você pode acompanhar o andamento de seus estudos através de suas skills.\r\n");
			builder.append("		</div>\r\n");
			builder.append("		<div align=\"left\">\r\n");
			builder.append("			<br/>\r\n");
			builder.append("		</div>\r\n");
			builder.append("		<div align=\"left\">\r\n");
			builder.append("			<br/>\r\n");
			builder.append("		</div>\r\n");
			builder.append("		<div align=\"left\">\r\n");
			builder.append("		</div>\r\n");
			builder.append("	</body>\r\n");
			builder.append("</html>\r\n");

			helper.setText(builder.toString(), true);
			emailSender.send(mensagemCadastro);

		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}
}