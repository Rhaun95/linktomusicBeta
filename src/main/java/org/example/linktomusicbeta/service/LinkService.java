package org.example.linktomusicbeta.service;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LinkService {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(LinkService.class);

    public void convertLinkToMusic(String link) {
        String downloadLocation  = System.getProperty("user.home") + "/Downloads/%(title)s.%(ext)s";
        
        String[] command = {
                "yt-dlp",
                "-x",
                "--audio-format", "aac",
                "--audio-quality", "320",
                "--no-mtime",
                "-o",downloadLocation,
                link
        };

        try {
            Process process = new ProcessBuilder(command).start();
            logger.info("Converting Youtube to MP3");

            printProcessOutput(process);

            int exitCode = process.waitFor();

            if(exitCode != 0) {
                logger.error("Converting failed! Code: " + exitCode);
                return;
            }
            logger.info("Successfully converted!");
        } catch (IOException | InterruptedException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 프로세스의 표준 출력 및 표준 오류를 콘솔에 출력
     *
     * @param process 실행 중인 프로세스
     * @throws IOException
     */
    private void printProcessOutput(Process process) throws IOException {
        // 표준 출력 스트림 읽기
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
    }

}

