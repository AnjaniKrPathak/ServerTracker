package com.example.servertracker.server.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PocOffering {
    private BigInteger flatOfferingId;
    private String name;
}
