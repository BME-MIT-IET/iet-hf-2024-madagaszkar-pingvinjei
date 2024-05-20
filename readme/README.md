# kozlegeny

Hogyan kell elindítani az alkalmazást gradle segítségével

1. JDK ellenőrzés (17)
    Nézd meg a gépeden feltelepített jdk verziót (cmd-ben java -version command segítségével megtehető)
        -ha jdk 8-17 körüli verziód van akkor nincs több teendő
        -ha jdk 17 feletti (lehet, hogy csak a 21 felettiekre igaz) akkor tegyél fel egy 17-es verziót
            -letöltöd a jdk-17-et és másold át a mappát valamilyen olyan helyre amihez lehetőleg hozzá tud férni a gép (tehát ne hagyd User alattiba)
            -utána windows-ban (tekintve, hogy nem használok más OS-t ezért azt most ne fogom leírni, hogy ott, hogy kell) search -> "Környezeti változók" (vagy System Variables, hogyha angol)![erre katt a találatokból](image.png)
        -utána jobb alsó sarokban "Környezeti Változók" (vagy System Variables) ![lásd itt](image-1.png)
        -a felső részben JAVA_HOME-ban megadod az elérési útját a letöltött mappának (nem bin-ig) ![lásd itt](image-2.png)
        -rendszerváltozóknál pedig a Path-re rányomsz -> Szerkesztés -> és átállítod az elérési utat a jdk-hoz arra a mappára amit letöltöttél ![mint itt](image-3.png)

2. Program futtatása
    A GitHub repo-ban csak a pipeline nevezetű mappára lesz szükségünk mert az tartalmazza a gradle build alatti programot
        -töltsd le majd tedd valamilyen külső mappába a pipeline mappát
        -ezt követően nyisd meg a cmd-t és cd-z be ebbe a mappába
        -írd be, hogy gradlew run
        -várj és elvileg kész is (ha megvan a megfelelő jdk verziód)
đ