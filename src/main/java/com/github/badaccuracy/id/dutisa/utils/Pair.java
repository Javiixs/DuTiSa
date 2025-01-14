package com.github.badaccuracy.id.dutisa.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Pair<K, V> {

    private final K key;
    private final V value;

}
