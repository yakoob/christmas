#include <Bridge.h>
#include <YunServer.h>
#include <YunClient.h>

YunServer server;

YunClient net;

int soundAnalogPin = 0;
int sound_value = 0;

void setup() {


  Bridge.begin();

  server.listenOnLocalhost();
  server.begin();

  Serial.begin(9600);

  digitalWrite(1, HIGH);
  digitalWrite(2, HIGH);
  digitalWrite(3, HIGH);
  digitalWrite(4, HIGH);
  digitalWrite(5, HIGH);
  digitalWrite(6, HIGH);
  digitalWrite(7, HIGH);
  digitalWrite(8, HIGH);

  delay(2000);

  digitalWrite(1, LOW);
  digitalWrite(2, LOW);
  digitalWrite(3, LOW);
  digitalWrite(4, LOW);
  digitalWrite(5, LOW);
  digitalWrite(6, LOW);
  digitalWrite(7, LOW);
  digitalWrite(8, LOW);

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

  // Check if the url contains the word "servo"
  if (command == "relay") {
    servoCommand(client);
  }

}

void servoCommand(YunClient client) {

  int pin;
  int value;

  // Get the servo Pin
  pin = client.parseInt();

  if (client.read() == '/') {

    value = client.parseInt();

    if (value == 1) {

      digitalWrite(pin, HIGH);

    } else if (value == 2) {

      digitalWrite(pin, HIGH);
      delay(250);
      digitalWrite(pin, LOW);

    } else if (value == 3) {

      allOff();

    } else if (value == 4) {

      allOn();

    }  else if (value == 5) {

      allBlink();

    }  else if (value == 6) {

      backAndForth();

    }  else if (value == 0) {

      digitalWrite(pin, LOW);

    }

  }

}

void allBlink(){
  allOff();
  allOn();
  delay(250);
  allOff();

}

void allOff() {
    digitalWrite(9, LOW);
    digitalWrite(2, LOW);
    digitalWrite(3, LOW);
    digitalWrite(4, LOW);
    digitalWrite(5, LOW);
    digitalWrite(6, LOW);
    digitalWrite(7, LOW);
    digitalWrite(8, LOW);
}

void allOn() {
    digitalWrite(9, HIGH);
    digitalWrite(2, HIGH);
    digitalWrite(3, HIGH);
    digitalWrite(4, HIGH);
    digitalWrite(5, HIGH);
    digitalWrite(6, HIGH);
    digitalWrite(7, HIGH);
    digitalWrite(8, HIGH);
}


void backAndForth() {
    digitalWrite(9, HIGH);
    delay(20);
    digitalWrite(2, HIGH);
    delay(20);
    digitalWrite(3, HIGH);
    delay(20);
    digitalWrite(4, HIGH);
    delay(20);
    digitalWrite(5, HIGH);
    delay(20);
    digitalWrite(6, HIGH);
    delay(20);
    digitalWrite(7, HIGH);
    delay(20);
    digitalWrite(8, HIGH);
    delay(20);
    digitalWrite(8, LOW);
    delay(20);
    digitalWrite(7, LOW);
    delay(20);
    digitalWrite(6, LOW);
    delay(20);
    digitalWrite(5, LOW);
    delay(20);
    digitalWrite(4, LOW);
    delay(20);
    digitalWrite(3, LOW);
    delay(20);
    digitalWrite(2, LOW);
    delay(20);
    digitalWrite(9, LOW);
    delay(20);
    digitalWrite(9, HIGH);
    delay(20);
    digitalWrite(2, HIGH);
    delay(20);
    digitalWrite(3, HIGH);
    delay(20);
    digitalWrite(4, HIGH);
    delay(20);
    digitalWrite(5, HIGH);
    delay(20);
    digitalWrite(6, HIGH);
    delay(20);
    digitalWrite(7, HIGH);
    delay(20);
    digitalWrite(8, HIGH);

}


