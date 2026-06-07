# language: lt

Savybė: Klientų valdymas

  Scenarijus: Naujo kliento sukūrimas
    Duota naujas klientas vardu "Jonas" ir pavarde "Jonaitis"
    Kai klientas išsaugomas
    Tada kliento vardas turi būti "Jonas"

  Scenarijus: Naujo kliento sukūrimas su kitu vardu
    Duota naujas klientas vardu "Petras" ir pavarde "Petraitis"
    Kai klientas išsaugomas
    Tada kliento vardas turi būti "Petras"