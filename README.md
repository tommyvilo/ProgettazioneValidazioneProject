# Sistema di Time Tracking Tool dei Progetti Accademici
## Università degli Studi di Verona - A.A. 2024-2025

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.0-green)
![Java](https://img.shields.io/badge/Java-21-blue)

## Descrizione del Sistema
Il sistema di **Time Tracking per Progetti Accademici** è una piattaforma sviluppata per assistere ricercatori, responsabili scientifici e amministratori nella gestione e nel monitoraggio delle ore lavorative dedicate ai progetti di ricerca. La piattaforma consente una rendicontazione efficace delle ore di lavoro, generando report mensili in formato .pdf, controfirmati dal responsabile scientifico.


## Glossario
- **Ricercatore (researcher)**: Utente che partecipa attivamente al progetto di ricerca, segnando le ore lavorate giornalmente
- **Responsabile Scientifico (supervisor)**: Referente del progetto, può gestire i ricercatori di quest’ultimo e ne approva i loro timesheet mensili, controfirmandoli
- **Amministratore (administrator)**: Utente che gestisce utenti (sia ricercatori che responsabili scientifici) e progetti
- **Report (report)**: Documento che riporta la rendicontazione oraria mensile del ricercatore
- **Firma (signature)**: Azione implicita eseguita quando il ricercatore segna e salva le ore lavorate giornalmente
- **Controfirma (countersignature)**: responsabile scientifico che valida il timesheet mensile del ricercatore sul progetto


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
- **Aggiunta Ore di Lavoro**: ricercatore e responsabile scientifico


## Scenari

**1)** Il ricercatore esegue con successo il login, seleziona la data desiderata e segna le ore di lavoro
relative ai progetti a cui sta lavorando. Successivamente conferma la scelta.
<br>

**2)** Il ricercatore esegue con successo il login e si porta all'interno della pagina relativa al download dei timesheet mensili relativi al progetto selezionato.
Successivamente scarica il timesheet mensile relativo al mese d'interesse in formato .pdf.

**3)** Il ricercatore esegue con successo il login, seleziona la data desiderata e segna la giornata come malattia/ferie.
Successivamente conferma la scelta.

**4)** Il responsabile scientifico esegue con successo il login, seleziona la data desiderata e segna le ore di lavoro
relative ai progetti a cui sta lavorando. Successivamente conferma la scelta.

**5)** Il responsabile scientifico esegue con successo il login e si porta all'interno della pagina di gestione dei progetti. Poi seleziona un progetto 
e aggiunge uno o piu ricercatori al progetto. Successivamente conferma la scelta.

**6)** Il responsabile scientifico esegue con successo il login e si porta all'interno della pagina di gestione dei progetti. Poi seleziona un progetto
e valida il timesheet mensile di uno specifico ricercatore.

**7)** L'amministratore esegue con successo il login e si porta all'interno della pagina relativa alla creazione di nuovi utenti. Poi inserisce le generalità del nuovo utente. Successivamente salva l'utente nel sistema.

**8)** L'amministratore esegue con successo il login e si porta all'interno della pagina relativa alla creazione di nuovi progetti. Poi inserisce le informazioni del nuovo progetto. Successivamente salva il progetto nel sistema.

## Testing

Effettuati con lo scopo di avere un high code coverage alto. (91% classes, 98% code lines)

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

**_testGetCookieByName_**<br>
Testato il sistema di acquisizione dati dal Cookie

### System Test
Sono stati eseguiti i seguenti test sugli scenari e sulle funzionalità del sistema.

**_testInsertWorkingHourByResearcher_**<br>
Il ricercatore inserisce le ore giornaliere, per poi salvarle.

**_testInsertWorkingHourBySupervisor_**<br>
Il responsabile scientifico inserisce le ore giornaliere, per poi salvarle.

**_testDownloadTimesheetByResearcher_**<br>
Il ricercatore accede ai vari timesheet del progetto e ne scarica uno accessibile.

**_testDownloadTimesheetBySupervisor_**<br>
Il responsabile scientifico accede ai vari timesheet personali del progetto e ne scarica uno accessibile.

**_testMalattiaWorkingTime_**<br>
Il ricercatore si mette in malattia per la giornata, ed è impossibilitato ad aggiungere le ore.

**_testAddingResearcherToProject_**<br>
Il responsabile scientifico seleziona un progetto ed aggiunge un ricercatore a quel progetto.

**_testRemovingResearcherFromProject_**<br>
Il responsabile scientifico seleziona un progetto e rimuove un ricercatore da quel progetto.

**_testValidateAndDownloadTimesheet_**<br>
Il responsabile scientifico controfirma e fa il download di un timesheet di un ricercatore del progetto che supervisiona.

**_testCreateResearcher_**<br>
L'amministratore aggiunge un utente ricercatore.

**_testCreateSupervisor_**<br>
L'amministratore aggiunge un utente responsabile scientifico.

**_testCreateProject_**<br>
L'amministratore accede e aggiunge un progetto.

**_testDeleteResearcher_**<br>
L'amministratore elimina un utente ricercatore.

**_testDeleteSupervisor_**<br>
L'amministratore ed elimina un responsabile scientifico.

**_testValidationAndDownload_**<br>
Il ricercatore nota che non c'è un timesheet scaricabile. <br>
Successivamente il responsabile scientifico di uno dei suoi progetti valida il timesheet ed infine il ricercatore scarica il timesheet validato.

**_testCreateResearcherAndLogin_**<br>
L'amministratore crea un utente ricercatore. <br>
Successivamente quest'ultimo accede alla piattaforma.

**_testCreateAndVerifyProject_**<br>
L'amministratore aggiunge un progetto con rispettivo responsabile scientifico.<br>
Successivamente il responsabile scientifico aggiunge un ricercatore al progetto ed infine il ricercatore designato accede e visualizza il nuovo progetto che gli è stato aggiunto.

**_testFailedLogin_**<br>
Test password invalida.

**_testInvalidUrlResearcher_**<br>
Test redirect per url invalida, lato ricercatore.

**_testInvalidUrlSupervisor_**<br>
Test redirect per url invalida, lato responsabile scientifico.

**_testInvalidUrlNotLoggedIn_**<br>
Test redirect per url invalida, lato utente non loggato.

**_testInvalidUrlAdministrator_**<br>
Test redirect per url invalida, lato amministratore.

**_testErrorPage_**<br>
Viene testata la pagina di errore.

## Autori
- Vilotto Tommaso - VR516306
- Zerman Nicolò - VR516333
