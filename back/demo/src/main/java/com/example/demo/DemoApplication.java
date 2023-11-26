package com.example.demo;

import com.example.demo.model.Playlist;
import com.example.demo.model.Track;
import com.example.demo.repository.*;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
@EnableAdminServer
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner populateTracks(TrackRepo trackRepo, PlaylistRepo playlistRepo, RoomRepo roomRepo) {
		return args -> {
			Track track1 = new Track();
			track1.setName("GigaChad Theme Phonk");
			track1.setAuthor("GigaChad");
			track1.setFileName("G3ox_em_-_GigaChad_Theme_Phonk_House_Version__CeeNaija.com_.mp3");
			track1.setPreviewUrl("https://mtsync.javastripters.ru/static/600px-Gigachad.jpg");

			Track track2 = new Track();
			track2.setName("Обпрмпт (Remix)");
			track2.setAuthor("Смешарики");
			track2.setFileName("Смешарики - Обпрмпт (Remix) - remixov.net.mp3");

  	 		Track track3 = new Track();
			track3.setName("Анекдоты 1 часть");
			track3.setAuthor("Роман Трахтенберг");
			track3.setFileName("Роман Трахтенберг Анекдоты 1 часть.mp3");

			Track track4 = new Track();
			track4.setName("Анекдоты 2 часть");
			track4.setAuthor("Роман Трахтенберг");
			track4.setFileName("Роман Трахтенберг Анекдоты 2 часть.mp3");

			Stream.of(track1, track2, track3, track4)
					.filter(t -> trackRepo.findByName(t.getName()).isEmpty())
					.forEach(trackRepo::save);


			Playlist playlist1 = new Playlist();
			playlist1.setName("Музыка для детей");
			playlist1.setTracks(List.of(track1, track2));

			Playlist playlist2 = new Playlist();
			playlist2.setName("Анекдоты для вхрослых");
			playlist2.setTracks(List.of(track3, track4));

			Stream.of(playlist1, playlist2)
					.filter(p -> playlistRepo.findByName(p.getName()).isEmpty())
					.forEach(playlistRepo::save);

		};
	}

	@Bean
	CommandLineRunner clearRooms(RoomRepo roomRepo, ParticipantRepo participantRepo) {
		return args -> {
			roomRepo.deleteAll();
			participantRepo.deleteAll();
		};
	}

	@Bean
	InitializingBean createTestEntries(JdbcTemplate jdbc) {
		return () -> {
			try {
				jdbc.update("alter table if exists participant add constraint participant_uniqe unique(user_id, participants_id)");
			} catch (Throwable t) {}
		};
	}





//	@Bean
//	CommandLineRunner stomp(RoomSyncService roomSyncService) {
//		return args -> {
//			WebSocketClient client = new StandardWebSocketClient();
//
//			WebSocketStompClient stompClient = new WebSocketStompClient(client);
//			stompClient.setMessageConverter(new MappingJackson2MessageConverter());
//
//			StompSessionHandler sessionHandler = new MyStompSessionHandler();
//			CompletableFuture<StompSession> stompSessionCompletableFuture = stompClient.connectAsync("ws://localhost:8080/ws", sessionHandler);
//			System.out.println(stompSessionCompletableFuture.get().getSessionId());
//			Thread.sleep(1000);
//
//			SyncMessage lastState = roomSyncService.getLastStateRenewed("123");
//			System.out.println(lastState);
//		};
//	}
}