# Texas hold'em

### Installing

`Java 8`
`Maven`

### Running

```
$ cd poker-server
$ mvn clean package
$ java -jar /target/poker-server-1.0-SNAPSHOT.jar
```

Input - `/src/main/resources/input.txt`:
```
TsQsKs3hJs 3 Kc3d Kh3c AhAc
TsQsKs3hJs 4 Kc3d Kh3c AhAc QcQh
TsQsKs3hJs 3 Kc3d Kh3c AhAcdf
TsQsKs3hJs 2 AhAc KyKo
TsKyKs3hJs 2 AhAc QcQh
TsQsKs3hJs 6 Kc3d Kh3c AhAc QcQh
TsQsKs3hJs 4 Kc3d Kh3c AhAc QcQc
TsQsKs3hJs 3 Kc3d KhKd AsAc
```

Output - `/src/main/resources/output.txt`:
```
AcAh(Straight) Kh3c(Two pairs) = Kc3d(Two pairs)
AcAh(Straight) QcQh(Three of a kind) Kh3c(Two pairs) = Kc3d(Two pairs)
String not matches a pattern!
Unknown hand cards found! First unknown hand - 2.
Unknown board cards found! First unknown board card - 2.
4 hands found, but number of hands in the source - 6. This is a problem.
Duplicate cards detected
AcAs(Straight flush !!!) KdKh(Three of a kind) Kc3d(Two pairs)
```