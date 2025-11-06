# Java előadás beadandó

## Használat (fejlesztéskor)

```text
Maven -> trade -> Lifecycle -> clean
Maven -> trade -> Lifecycle -> install
Maven -> trade -> Lifecycle -> package
```

## Használat (feladat leadás után)

A JAR állományt a gyökérkönyvtárból kell futtatni:

```sh
java -jar ./beadando.jar
```

----

## Fejlesztési Folyamat (Git Workflow)

A célunk, hogy a `master` ág mindig a stabil, működő kódot tartalmazza, a fejlesztés pedig átlátható és követhető legyen.

Mindenki a saját, előre létrehozott fejlesztési ágán (`branch`-én) dolgozik. A kész kód csak felülvizsgálat után, egy **Merge Request** (beolvasztási kérelem) segítségével kerülhet be a `master` ágba, melyet ezen repó tulajdonosa végzi majd el.

### Első lépések: A projekt letöltése (klónozás)

Ha még nincs meg a gépeden a projekt, először klónoznod kell a GitHub repository-t.

1.  Menj a projekt GitHub oldalára.
2.  Kattints a kék **"Clone"** gombra a jobb felső sarokban.
3.  Másold ki a **"Clone with HTTPS"** linket.
4.  Nyiss egy terminált (parancssort) a gépeden, navigálj abba a mappába, ahova a projektet szeretnéd letölteni, és futtasd a következő parancsot:

```text
git clone https://github.com/LantosIstvan/trade.git
```

Ezzel létrejön a gépeden egy `LogbookApp` mappa a projekt teljes tartalmával.

### A Munkafolyamat Lépésről Lépésre

#### 1. Válts át a saját, dedikált branch-edre

Válts át a saját branch-edre:

```sh
git checkout gyorgyi
```

Ezzel átkerültél a saját fejlesztési ágadra. Innentől minden változtatás, amit elmentesz, csak ezen a branch-en fog megjelenni.

#### 2. Frissítsd a branch-edet a legújabb `master`-ről

Mielőtt bármilyen kódolásba kezdenél, elengedhetetlen, hogy a saját branch-edet naprakész állapotba hozd a `master` ág legfrissebb változásaival. Ezzel biztosítod, hogy a legújabb kóddal dolgozol, és elkerülöd a későbbi konfliktusokat.

```sh
# Győződj meg róla, hogy a saját branch-eden vagy (lásd fentebb!)

# 1. Töltsd le a legfrissebb állapotot a szerverről (ez még nem módosítja a kódodat)
git fetch origin

# 2. Fésüld össze a szerveren lévő master ág frissítéseit a te branch-eddel
git merge origin/master
```

Ha a `merge` parancs lefut hiba nélkül, a branch-ed naprakész. Ha **konfliktust** jelez, a Git a fájlokban jelöli a problémás részeket, amiket kézzel kell javítanod. A javítás után a `git add .` és `git commit` parancsokkal fejezd be a merge-et.

#### 3. Dolgozz a kódon

Most, hogy a branch-ed naprakész, írd meg a kódot, fejleszd le a funkciót.

#### 4. Mentsd el a változtatásaidat (Commit)

Ha elértél egy logikai egységet, készíts egy `commit`-ot.

```sh
# Add hozzá a változtatott fájlokat a commithoz (a pont az összeset jelenti)
git add .

# Készítsd el a commit-ot egy üzenettel
git commit -m "Leíró üzenet"
```

#### 5. Töltsd fel a munkádat a GitHub-re (Push)

Amikor fel akarod tölteni a munkádat a központi repository-ba, használd a `push` parancsot.

```sh
# Feltölti a commit-jaidat a saját branch-edre
git push
```

Ezzel a `master` ág nem változik, csak a te saját ágad frissül a GitHub-on.

A repótulajdonos beemeli a kódot a `master` ágba, nincs szükség Merge Request létrehozására.
