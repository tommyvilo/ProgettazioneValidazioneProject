# Sistema di Time Tracking Tool dei Progetti Accademici
## Università degli Studi di Verona - A.A. 2024-2025

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.0-green)
![Java](https://img.shields.io/badge/Java-21-blue)

## Descrizione del Sistema
Il sistema di **Time Tracking per Progetti Accademici** è una piattaforma sviluppata per assistere ricercatori, supervisori e amministratori nella gestione e nel monitoraggio delle ore lavorative dedicate ai progetti di ricerca. La piattaforma consente una rendicontazione efficace delle ore di lavoro, generando report mensili in formato .pdf, controfirmati dal supervisore.


## Glossario
- **Ricercatore (researcher)**: Utente che partecipa attivamente al progetto di ricerca, segnando le ore lavorate giornalmente
- **Responsabile Scientifico (supervisor)**: Referente del progetto, può gestire i ricercatori di quest’ultimo e ne approva i loro timesheet mensili, controfirmandoli
- **Amministratore (administrator)**: Utente che gestisce utenti (sia ricercatori che supervisori) e progetti
- **Report (report)**: Documento che riporta la rendicontazione oraria mensile del ricercatore
- **Firma (signature)**: Azione implicita eseguita quando il ricercatore segna e salva le ore lavorate giornalmente
- **Controfirma (countersignature)**: supervisore che valida il timesheet mensile del ricercatore sul progetto


## Obiettivi del Sistema
Il sistema di **Time Tracking per Progetti Accademici** è progettato per supportare e semplificare il processo di rendicontazione delle ore di lavoro mensili da parte dei ricercatori dei progetti di ricerca.

Il sistema deve supportare le seguenti funzionalità principali:
- Rendicontazione delle ore lavorative del ricercatore
- Gestione e generazione dei report mensili
- Creazione e gestione di utenti e progetti
- Gestione dei permessi per l’accesso alle varie funzionalità


## Permessi
- **Creazione Progetti**: amministratore
- **Creazione Utenti**: amministratore
- **Gestione Progetti** (mockup): amministratore
- **Gestione Utenti** (mockup): amministratore
- **Aggiunta Ricercatori al Progetto**: responsabile scientifico
- **Validazione Report Mensili**: responsabile scientifico
- **Modifica Report Mensili** (mockup): responsabile scientifico
- **Lettura Report Mensili**: ricercatore e responsabile scientifico
- **Creazione Report Mensili**: ricercatore e responsabile scientifico
- **Rendicontazione Ore di Lavoro Giornaliere**: ricercatore


## Scenari

Tutti gli scenari qui sotto elencati sono effettuati una volta che l'utente ha eseguito il login.

1) Il ricercatore segna le ore giornalmente relative ai progetti su cui sta lavorando
2) Il ricercatore accede ai vari timesheet del progetto e scarica il primo accessibile
3) Il ricercatore si mette in malattia per la giornata, ed è impossibilitato ad aggiungere le ore
4) Il responsabile scientifico seleziona un progetto ed aggiunge ricercatori a quel progetto
5) Il responsabile scientifico controfirma il timesheet del ricercatore relativo ad un progetto
6) L’amministratore aggiunge un utente
7) L’amministratore aggiunge un progetto e relativo responsabile scientifico


## Testing

Effettuati con lo scopo di avere un high code coverage alto. (91% classes, 92% code lines)

### Unit Test

**_testResearcherUnits_**<br>
Testata interamente la classe Researcher

**_testSupervisorUnits_**<br>
Testata interamente la classe Supervisor

**_testAdministratorUnits_**<br>
Testata interamente la classe Administrator

**_testProjectUnits_**<br>
Testata interamente la classe Project

**_testWorkingTimeUnits_**<br>
Testata interamente la classe WorkingTime

### System Test
Per svolgere i test di sistema abbiamo preso come riferimento gli scenari sopra definiti. <br>
Di conseguenza scenario1, scenario2... non sono altro che i test dei vari scenari presi singolarmente. <br>
In ogni test scenario viene effettuato il login prima di eseguire le varie azioni e il logout alla fine.

**_scenario1_**<br>
**_scenario2_**<br>
**_scenario3_**<br>
**_scenario4_**<br>
**_scenario5_**<br>
**_scenario6_**<br>
**_scenario7_**<br>

Oltre ai test dei vari scenari, sono stati effettuati ulteriori system test

**_testDeleteUser_**<br>
Dopo aver effettuato l'accesso, l'amministratore entra nella pagine di Gestione Utenti ed elimina un utente.

**_testValidationAndDownload_**<br>
Il ricercatore verifica di non avere nessun timesheet scaricabile. Successivamente il supervisore approva il timesheet del ricercatore e infine quest'ultima riuscirà a scaricarlo.

**_testCreateResearcher_**<br>
L'amministratore crea un utente Ricercatore e successivamente il nuovo ricercatore effettua il login.

**_testCreateAndVerifyProject_**<br>
L'amministratore crea un nuovo progetto, specificando un supervisore. <br>
Successivamente il supervisore andrà ad aggiungere un ricercatore che verificherà di poter aggiungere le ore lavorate su quel nuovo progetto.

## Autori
- Vilotto Tommaso - VR516306
- Zerman Nicolò - VR516333
