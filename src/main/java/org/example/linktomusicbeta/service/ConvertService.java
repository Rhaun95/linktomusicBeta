package org.example.linktomusicbeta.service;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConvertService {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(ConvertService.class);

    public static String getAudioStreamUrl(String link) throws IOException, InterruptedException {
        String[] command = {
                "yt-dlp",
                "-f", "bestaudio",
                "--get-url",
                link
        };

        Process process = new ProcessBuilder(command).start();
        logger.info("Fetching YouTube audio stream URL...");

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String audioUrl = reader.readLine();  // 첫 번째 출력 줄이 오디오 URL

        int exitCode = process.waitFor();
        if (exitCode != 0 || audioUrl == null || audioUrl.isEmpty()) {
            logger.error("Failed to retrieve audio URL! Exit Code: " + exitCode);
            return null;
        }

        logger.info("Audio URL: " + audioUrl);
        return audioUrl;
    }

    public static String convertLinkToMusic(String link) throws IOException, InterruptedException {
        String downloadLocation  = System.getProperty("user.home")+ File.separator+ "Music/%(title)s.%(ext)s";
        
        String[] command = {
                "yt-dlp",
                "-x",
                "--audio-format", "mp3",
                "--audio-quality", "190",
                "--no-mtime",
                "--embed-metadata",
                "--embed-thumbnail",
                "-o",downloadLocation,
                link
        };

        Process process = new ProcessBuilder(command).start();
        logger.info("Converting Youtube to MP3");


        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);  // 콘솔에 출력
        }

        // 표준 오류 스트림 읽기
        BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        while ((line = errorReader.readLine()) != null) {
            System.err.println(line);  // 오류 메시지 콘솔에 출력
        }

        int exitCode = process.waitFor();
        if(exitCode != 0) {
            logger.error("Converting failed! Code: " + exitCode);

        }
        logger.info("Successfully converted!");
        return reader.readLine();

    }

    /**
     * 프로세스의 표준 출력 및 표준 오류를 콘솔에 출력
     *
     * @param process 실행 중인 프로세스
     * @throws IOException
     */
    private static void printProcessOutput(Process process) throws IOException {
        // 표준 출력 스트림 읽기

    }

}

