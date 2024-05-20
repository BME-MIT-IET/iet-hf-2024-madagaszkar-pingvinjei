### DOKUMENTÁCIÓ - *Madagaszkár Pingvinjei*

#

#

### *Technológia 2*
# SonarQube beüzemelése
## Célkitűzés
*Statikus hibaanalízis érdekében SonarQube beüzemelése, futtatása.*

## Leírás
#### SonarQube
A SonarQube fő célja a letisztult, optimális "Clean Code", amit a kódunk automatikus felülvizsgálatával ér el, és amivel támogatni tudja a CI/CD megvalósítását. Ezt különböző tesztelésekkel éri el. Az általa ajánlott változtatások akár a projekt architektúrájára is hatással lehetnek.

## Munkafolyamat
Ez a szoftver volt csapatunk számára ez egyik leghasznosabb technológia, amit a repository "Issue" logjának megtekintésével egyértelműen észre lehet venni.

Rengeteg hibát és javaslatot jelzett nekünk a projektben, amik egyszerű átírásoktól akár az osztályok struktúrájának polimorfizmussal történő átépítését is magukkal vonhatták.

Az első lépés a szoftver hozzáadása volt a repository-hoz. Ezt a letöltése után egy új Git-es "*secret*" létrehozásával tudtuk hozzáadni a repository-hoz, majd pedig a létrejött konfigurációs file-ban a kulcs beállításával véglegesíteni.

Ezek után korábban említett Workflow file-unkban tudtuk a SonarQube működését definiálni.

Alább megtekinthető a SonarQube implementációs Workflow file-ra egy példa.

![workflow példa sq-val](res/sq_2.png)

A SonarQube összesen 8 különböző osztályban talált eltérő súlyosságú hibát, amik javításával sokkal értelmesebb, átláthatóbb és kezelhetőbb lett az alkalmazás osztály-, és függvénykészlete, illetve maga a szoftverarchitektúra is.

Ezekből a javításokból szemléltetésképp itt felsorolunk hármat:

*1: Kasztolás elkerülése megfelelő függvények meghívásával*

![példa 1](res/sq_ex_1.png)

*2: Láthatóság korlátozása a biztonság érdekében*

![példa 2](res/sq_ex_2.png)

*3: Kódoptimalizálás megfelelő lista struktúra használatával*

![példa 3.a](res/sq_ex_3a.png)

![példa 3.b](res/sq_ex_3b.png)

A SonarQube-os integráció végső kimenetét megtekinthetjük az alábbi képen.

![sq teszt kimenet](res/sq_1.png)
