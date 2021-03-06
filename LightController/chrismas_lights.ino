#include <Bridge.h>
#include <YunServer.h>
#include <YunClient.h>

YunServer server;

YunClient net;

int soundAnalogPin = 0;
int sound_value = 0;


/**
* pin 9 : side yard: left palm tree
* pin 3 : side yard: right palm tree
* pin 4 : front house: left palm tree
* pin 5 : front house: right palm tree
* pin 6 : house: left roof
* pin 7 : house: right roof
*/
void setup() {


  Bridge.begin();

  server.listenOnLocalhost();
  server.begin();

  Serial.begin(9600);

  for (int thisPin = 1; thisPin <= 8; thisPin++) {
    digitalWrite(thisPin, HIGH);  
  }

  delay(2000);

  for (int thisPin = 1; thisPin <= 8; thisPin++) {
    digitalWrite(thisPin, LOW);  
  }

  delay(1000);

}

void loop() {

  YunClient client = server.accept();

  if (client) {
    process(client);
    client.stop();
  }

}

void process(YunClient client) {

  String command = client.readStringUntil('/');

  // Check if the url contains the word "relay"
  if (command == "relay") {
    relayCommand(client);
  }

}

void relayCommand(YunClient client) {

  int pin;
  int value;

  // Get the arduino Pin
  pin = client.parseInt();

  if (client.read() == '/') {

    value = client.parseInt();

    if (value == 1) { // turn pin on

        digitalWrite(pin, HIGH);

    } else if (value == 2) { // blink pin

        digitalWrite(pin, HIGH);
        normalDelay();
        digitalWrite(pin, LOW);

    } else if (value == 3) { // all pins off

        allOff();

    } else if (value == 4) { // all pins on

        allOn();

    }  else if (value == 5) { // all pins blink

        allBlink();

    }  else if (value == 6) { // all pins crawl

        crawl();

    }  else if (value == 7) { // all pins back and forth quickly

       backAndForth();

    }  else if (value == 8) { // all pins from outer in

       outerIn();

    }  else if (value == 9) { // all pins inner out

       innerOut();

    }  else if (value == 10) { // all pins go crazy

        bannanas();

    }  else if (value == 0) { // turn pin off

        digitalWrite(pin, LOW);

    }

  }

}


void reset(){
  allOff();
  fastDelay();
}

void fastDelay(){
  delay(100);
}

void normalDelay(){
  delay(250);
}

void allBlink(){
  allOff();
  allOn();
  normalDelay();
  allOff();
}

void allOff() {
    digitalWrite(9, LOW);
    digitalWrite(3, LOW);
    digitalWrite(4, LOW);
    digitalWrite(5, LOW);
    digitalWrite(6, LOW);
    digitalWrite(7, LOW);
}

void allOn() {
    digitalWrite(9, HIGH);
    digitalWrite(3, HIGH);
    digitalWrite(4, HIGH);
    digitalWrite(5, HIGH);
    digitalWrite(6, HIGH);
    digitalWrite(7, HIGH);
}


void outerIn(){
    reset();

    digitalWrite(9, HIGH);
    digitalWrite(7, HIGH);
    fastDelay();

    digitalWrite(3, HIGH);
    digitalWrite(6, HIGH);
    fastDelay();

    digitalWrite(4, HIGH);
    digitalWrite(5, HIGH);
}

void innerOut(){
    reset();

    digitalWrite(4, HIGH);
    digitalWrite(5, HIGH);
    fastDelay();

    digitalWrite(3, HIGH);
    digitalWrite(6, HIGH);
    fastDelay();

    digitalWrite(9, HIGH);
    digitalWrite(7, HIGH);
}

void backAndForth() {
    crawl();
    crawl();
}

void crawl(){
    reset();

    fastDelay();
    digitalWrite(9, HIGH);
    fastDelay();
    digitalWrite(3, HIGH);
    fastDelay();
    digitalWrite(4, HIGH);
    fastDelay();
    digitalWrite(5, HIGH);
    fastDelay();
    digitalWrite(6, HIGH);
    fastDelay();
    digitalWrite(7, HIGH);
    fastDelay();
    digitalWrite(7, LOW);
    fastDelay();
    digitalWrite(6, LOW);
    fastDelay();
    digitalWrite(5, LOW);
    fastDelay();
    digitalWrite(4, LOW);
    fastDelay();
    digitalWrite(3, LOW);
    fastDelay();
    digitalWrite(9, LOW);
}

void bannanas(){
  outerIn();
  innerOut();
  backAndForth();
  innerOut();
  allOn();
}

