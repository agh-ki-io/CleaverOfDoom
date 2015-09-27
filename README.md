##W projekcie używany jest build tool gradle, podstawowe komendy:

##Kompilacja:
  * gradlew build
  
##Uruchamianie:
  * android - gradlew android:run (przy użyciu adb, wymaga uruchomionej maszyny wirtualnej z systemem android)
  * desktop - gradlew desktop:run
  
##Pakowanie:
  * android - gradlew android:assemble(Debug lub Release)
  * desktop - gradlew desktop:assemble
  
Używane pluginy do gradle: Java, Android, Eclipse, Idea w razie potrzeby bardziej twórczych komend odsylam do dokumentacji
https://docs.gradle.org/current/userguide/userguide

Ponadto w głównym folderze należy dodać plik 'local.properties' zawierający:

sdk.dir=<ścieżka do Android SDK>

dla przykładu:

sdk.dir=/home/lgmyrek/SDK_RE/Android/android-sdk-linux .

##Po uruchomieniu programu
Klawisz 'Enter' - rozpoczęcie.  
Klawisze 'w', 's', 'a' oraz 'd' - ruch w czterech kierunkach.  
Klawisze 'u', 'i' oraz 'o' - użycie umiejętności postaci.
