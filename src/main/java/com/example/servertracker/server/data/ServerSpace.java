package com.example.servertracker.server.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServerSpace {
   String fileSystem;
   String size;
   String used;
   String avail;
   String use;
   String mountedOn;
}
