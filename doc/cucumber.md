### DOKUMENTÁCIÓ - *Madagaszkár Pingvinjei*

#

#

# Cucumber beüzemelése
## Célkitűzés
*BDD tesztek készítése Cucumber-rel, egyszerűbb use-case-ek megfogalmazása.*

## Leírás
#### Cucumber
A Cucumber egy tesztautomatizálási eszköz, amely támogatja a Behavior Driven Development (BDD) módszertant. Lehetővé teszi a tesztelők és fejlesztők számára, hogy egy természetes nyelvű formátumban írják meg a teszteket, amelyeket a Cucumber képes végrehajtani.

## Munkafolyamat
A Cucumber használatával különböző teszteseteket definiáltunk, amelyek a projekt különböző funkcióit fedik le. A Cucumber tesztfájlok négy fő csoportba sorolhatók: StepDefinitions, Mechanic Actions, Player Actions és Saboteur Actions.

### StepDefinitions.java
#### Tesztek leírása
- **Példányok létrehozása:** A különböző típusú játékosok (játékos, szerelő, szabotőr) és konténerek (cső, szivattyú, ciszterna) példányosítása.
- **Inicializálás és bool check:** A szivattyú és a cső szomszédságának beállítása, a játékos pozíciójának meghatározása (cső, szivattyú, ciszterna), valamint a cső állapotának ellenőrzése (sérült, ragadós, csúszós).
- **Action végrehajtása:** A játékos különböző műveleteket hajt végre, mint például a szivattyú javítása, a cső javítása, a szivattyú mozgatása, a cső ragadóssá tétele, vagy a szivattyú átvétele a ciszternából.

### mechanic_actions.feature
#### Funkciók és tesztesetek
- **Mechanic repairs a pump:** A szerelő megjavít egy sérült szivattyút.
- **Mechanic repairs a pipe:** A szerelő megjavít egy sérült csövet.

### player_actions.feature
#### Funkciók és tesztesetek
- **Player moves to a pump from a pipe:** A játékos egy csőből mozog egy szivattyúba.
- **Player moves to a pipe from a pump:** A játékos egy szivattyúból mozog egy csőbe.
- **Player moves to a slippery pipe from a pump:** A játékos egy csúszós csőbe mozog egy szivattyúból, de nem marad ott.
- **Player moves to a sticky pipe from a pump:** A játékos egy ragadós csőbe mozog egy szivattyúból, és ragadós lesz.
- **Player tries to move to a pump from a pipe while being sticky:** A játékos ragadós állapotban próbál meg mozogni egy csőből egy szivattyúba, de nem sikerül.
- **Player makes a pipe sticky:** A játékos ragadóssá teszi a csövet.
- **Player takes a pump from the cistern:** A játékos átveszi a szivattyút a ciszternából.

### saboteur_actions.feature
#### Funkciók és tesztesetek
- **Saboteur makes a pipe slippery:** A szabotőr csúszóssá teszi a csövet.

## Cucumber tesztek futásának eredménye

#

### Mechanic Actions tesztek eredménye
<img src = "res/cu_mec.png" width = "500">

#

### Player Actions tesztek eredménye
<img src = "res/cu_pla.png" width = "600">

#

### Saboteur Actions tesztek eredménye
<img src = "res/cu_sab.png" width = "500">

#

### Teljes teszt eredménye
<img src = "res/cu_all.png" width = "200">