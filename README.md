<a name="readme-top"></a>
<div align="center">
<h3 align="center">Australian Open 2026


Volunteer Management System</h3>

<p align="center">
Softverski sistem za upravljanje prijavama volontera na teniskom turniru.
<br />
Klijent-Server Arhitektura | Java Swing | Socket Komunikacija
</p>
</div>

<details>
<summary>Sadržaj</summary>
<ol>
<li>
<a href="#o-projektu">O projektu</a>
<ul>
<li><a href="#korišćene-tehnologije">Korišćene tehnologije</a></li>
</ul>
</li>
<li>
<a href="#struktura-projekta">Struktura projekta</a>
</li>
<li>
<a href="#ključne-funkcionalnosti">Ključne funkcionalnosti</a>
</li>
<li>
<a href="#pokretanje-sistema">Pokretanje sistema</a>
</li>
<li><a href="#poslovna-logika-i-validacija">Poslovna logika</a></li>
</ol>
</details>

---

## O projektu

Ovaj projekat predstavlja robusnu **Klijent-Server aplikaciju** dizajniranu za automatizaciju procesa prijavljivanja volontera za prestižni turnir Australian Open 2026. Sistem omogućava volonterima registraciju, prijavu na smene i praćenje statusa prijava, dok server upravlja konkurentnim pristupom, validacijom podataka i perzistentnošću.

Aplikacija je razvijena kao deo predispitnih obaveza iz predmeta Mrežno programiranje, sa fokusom na rad sa nitima (Multithreading), TCP/IP komunikaciju i objektno-orijentisani dizajn.

### Korišćene tehnologije

* **Java JDK 17**

<p align="right">(<a href="#readme-top">vrh</a>)</p>

## Struktura projekta

Sistem je implementiran kroz **Monorepo** arhitekturu sa tri nezavisna modula:

```text
AustralianOpen2026/
├── Server/           # Serverska aplikacija (Backend logika, Threads, File I/O)
├── Client/           # Klijentska aplikacija (GUI, Forme, Validacija unosa)
└── Common/           # Zajednička biblioteka (Domain classes, Transfer Objects)

```

<p align="right">(<a href="#readme-top">vrh</a>)</p>

## Ključne funkcionalnosti

### Mrežna komunikacija

* **Višekorisnički rad:** Server podržava rad sa više klijenata istovremeno koristeći `Multithreading` (svaki klijent ima svoj `ClientHandler`).
* **Graceful Shutdown:** Implementiran mehanizam za bezbedno gašenje servera koji automatski obaveštava i gasi sve povezane klijente.

### Upravljanje korisnicima

* **Registracija:** Kreiranje naloga sa validacijom jedinstvenosti (Username, JMBG, Email).
* **Autentifikacija:** Bezbedan login sistem.
* **Validacija unosa:** Regex provera formata JMBG-a (13 cifara) i email adrese.

### Upravljanje prijavama

* **Kreiranje prijave:** Odabir datuma, smene i pozicije.
* **Pregled:** Tabelarni prikaz svih prijava ulogovanog korisnika.
* **Dinamički statusi:** Automatsko određivanje statusa prijave (`U OBRADI`, `ZAKLJUČANA`, `ZAVRŠENA`) na osnovu vremena preostalog do smene.
* **Izmena i Otkazivanje:** Mogućnost manipulacije prijavama uz vremenska ograničenja (24h pravilo).
* **Generisanje potvrde:** Automatsko kreiranje `.txt` fajla na klijentskoj strani kao dokaz o uspešnoj prijavi.

<p align="right">(<a href="#readme-top">vrh</a>)</p>

## Poslovna logika i Validacija

Sistem implementira stroga ograničenja definisana specifikacijom:

| Pravilo | Opis |
| --- | --- |
| **Datum** | Prijave su moguće samo za buduće datume, zaključno sa finalom (01.02.2026). |
| **Frekvencija** | Maksimalno **jedna** prijava po korisniku za isti datum. |
| **Pozicije** | Korisnik može birati najviše **dve različite vrste** pozicija tokom celog turnira. |
| **Smene** | Limitirano na **3 večernje** i **5 popodnevnih** smena po korisniku. |
| **Zaključavanje** | Prijave se ne mogu menjati niti otkazati ako je do smene ostalo manje od 24h. |

<p align="right">(<a href="#readme-top">vrh</a>)</p>

## Pokretanje sistema

Za pokretanje aplikacije potreban je instaliran Java Development Kit (JDK) i IDE (NetBeans/IntelliJ).

1. **Klonirajte repozitorijum**
```sh
git clone https://github.com/blupxd/AO2026---RMT-Domaci-2.git
```


2. **Kompajliranje (Build)**
* Prvo kompajlirati projekat `Common` (jer zavisnosti idu ka njemu).
* Zatim kompajlirati `Server` i `Client`.


3. **Pokretanje Servera**
* Pokrenite klasu `forms.FrmServer`.
* Kliknite na dugme **"Pokreni Server"**.


4. **Pokretanje Klijenta**
* Pokrenite klasu `forms.FrmLogin`.
* Registrujte se ili ulogujte (Default admin: `admin` / `admin`).



<p align="right">(<a href="#readme-top">vrh</a>)</p>

---

<div align="center">
Matija Stefanović &copy; 2026
</div>
