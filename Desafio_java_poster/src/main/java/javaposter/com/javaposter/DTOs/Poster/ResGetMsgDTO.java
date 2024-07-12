package javaposter.com.javaposter.DTOs.Poster;

import java.util.List;

public record ResGetMsgDTO(List<Res_All_MessagesDTO> posters, String message) {
}
