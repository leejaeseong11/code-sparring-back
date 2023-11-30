package com.trianglechoke.codesparring.room.dto;


import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomMemberDTO {
    private Long roomNo;
    private Long memberNo;
    private Integer hostStatus;
}
