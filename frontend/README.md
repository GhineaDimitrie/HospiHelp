# Sănătatea noastră - frontend Spring Boot

Proiectul a fost refăcut după schema din documentul Word și include:
- pagină de login custom
- autentificare cu 4 roluri
- redirecționare pe dashboard în funcție de rol
- pagini frontend pentru modulele cerute

## Conturi demo

| Rol | Utilizator | Parolă |
|---|---|---|
| Medic | medic@sn.ro | medic123 |
| Recepționist | receptie@sn.ro | receptie123 |
| Farmacist | farmacie@sn.ro | farmacie123 |
| Administrator | admin@sn.ro | admin123 |

## Pornire

1. Deschide proiectul în IntelliJ IDEA
2. Lasă Maven să descarce dependențele
3. Rulează clasa `SanatateaNoastraFrontendApplication`
4. Accesează `http://localhost:8080`

## Observații

- Loginul este funcțional, cu utilizatori in-memory
- Proiectul este concentrat pe frontend și pe fluxul de autentificare
- Backend-ul real pentru baze de date și operații CRUD poate fi adăugat ulterior
