package com.mykare.in.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mykare.in.entity.Users;
import com.mykare.in.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommonFunctionUtils {

	private static final ZoneId ASIA_KOLKATA = ZoneId.of("Asia/Kolkata");
	private final UsersRepository usersRepository;
	private final PasswordEncoder passwordEncoder;

	public static Timestamp getCurrentTimeStamp()
	{
		TimeZone.setDefault(TimeZone.getTimeZone(ASIA_KOLKATA));
		Date now = new Date();
		return new Timestamp(now.getTime());
	}

	public boolean isValidUser(String username, String password) {
		Optional<Users> user=usersRepository.findByEmail(username);
		if(user.isPresent())
		{
			String encodedPassword=user.get().getPassword();
			return passwordEncoder.matches(password, encodedPassword);
		}
		else {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
	}

	public String[] extractCredentials(HttpServletRequest servletRequest )
	{
		String[] basicPassword = servletRequest.getHeader("Authorization").split("Basic");

		if (basicPassword.length > 1) {

			byte[] decodedBytes = Base64.getDecoder().decode(basicPassword[1].trim());
			String decodedBase64 = new String(decodedBytes, StandardCharsets.UTF_8);
			String[] userDetails = decodedBase64.split(":");
			if (userDetails.length == 2) {
				String username = userDetails[0];
				String password = userDetails[1];
				return new String[]{username,password};

			}
			else {
				throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
			}
		}
		else {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
	}

	public static String getIp()
	{
		String ipAddress="";
		try (Scanner s = new Scanner(new URL("https://api.ipify.org").openStream(), StandardCharsets.UTF_8).useDelimiter("\\A")) {
			ipAddress = s.next();
		} catch (IOException e)
		{
			log.error("error",e);
		}
		return ipAddress;
	}

	public static String getCountry(String ip)
	{
		String country="";
		try (Scanner s = new Scanner(new URL("http://ip-api.com/json/"+ip).openStream(), StandardCharsets.UTF_8).useDelimiter("\\A"))
		{
			String jsonResponse = s.next();
			JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
			country = jsonObject.get("country").getAsString();
		} catch (IOException e)
		{       log.error("error",e);    }
		return country;
	}
}
