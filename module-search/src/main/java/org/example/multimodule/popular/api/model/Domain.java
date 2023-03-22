package org.example.multimodule.popular.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

@Setter
@Getter
@NoArgsConstructor
public class Domain extends Popular {
    String Domain;
    BigInteger Count;

}
