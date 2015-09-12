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

Dodajcie sobie plik local.properties w glownym folderze z:

sdk.dir=Path.to.android.sdk

np.

sdk.dir=/home/lgmyrek/SDK_RE/Android/android-sdk-linux

