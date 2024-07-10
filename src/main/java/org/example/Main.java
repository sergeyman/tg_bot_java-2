package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.*;

//public class Main implements LongPollingBot {
public class Main extends TelegramLongPollingBot {
    // User name: @ASV_2_Bot
    // API Token: 6094929465:AAFZMTflwJuPNpu89LE7K85XY_t47MhdC-0

    /*
    Привіт!
    [КНОПКА] Слава Україні!

    Героям Слава!
    [КНОПКА] Слава Нації!

    Смерть ворогам!
     */

    // 3)
    private Map<Long, Integer> levels = new HashMap<>();

    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(new Main());

        //System.out.println("Hello world!");
    }

    @Override
    public String getBotUsername() {
        return "@ASV_2_Bot";
    }

    @Override
    public String getBotToken() {
        return "6094929465:AAFZMTflwJuPNpu89LE7K85XY_t47MhdC-0";
    }

    @Override
    public void onUpdateReceived(Update update) {
        // Отправить Hello на любое действие
        Long chatId = getChatId(update);

//        SendMessage msg = createMessage("*Hello* Sergii!");
//
//        // 2) + buttons
//        attachButtons(msg, Map.of("BTN 1", "hello_btn_1",
//                "BTN 2", "hello_btn_2"));
//
//        msg.setChatId(chatId);
//        sendApiMethodAsync(msg);          // Повідомлення

        // 2-2) ОБработка входного сообщения (из чата ТГ)
        if(update.hasMessage() && update.getMessage().getText().equals("/start")) {
//            SendMessage message = createMessage("Привіт!");
//            message.setChatId(chatId);
//            attachButtons(message, Map.of(
//                    "Слава Україні", "glory_for_ukraine"
//            ));
//            sendApiMethodAsync(message);

            // 3)
            sendImage("level-1", chatId);

            SendMessage message = createMessage("Ґа-ґа-ґа!\n" +
                    "Вітаємо у боті біолабораторії «Батько наш Бандера».\n" +
                    "\n" +
                    "Ти отримуєш гусака №71\n" +
                    "\n" +
                    "Цей бот ми створили для того, щоб твій гусак прокачався з рівня звичайної свійської худоби до рівня біологічної зброї, здатної нищити ворога. \n" +
                    "\n" +
                    "Щоб звичайний гусак перетворився на бандерогусака, тобі необхідно:\n" +
                    "✔️виконувати завдання\n" +
                    "✔️переходити на наступні рівні\n" +
                    "✔️заробити достатню кількість монет, щоб придбати Джавеліну і зробити свєрхтра-та-та.\n" +
                    "\n" +
                    "*Гусак звичайний.* Стартовий рівень.\n" +
                    "Бонус: 5 монет.\n" +
                    "Обери завдання, щоб перейти на наступний рівень");

            message.setChatId(chatId);

            // 4.2) + random
            List<String> buttons = Arrays.asList(
                    "Сплести маскувальну сітку (+15 монет)",
                    "Зібрати кошти патріотичними піснями (+15 монет)",
                    "Вступити в Міністерство Мемів України (+15 монет)",
                    "Запустити волонтерську акцію (+15 монет)",
                    "Вступити до лав тероборони (+15 монет)"
            );
            buttons = getRandom3(buttons);

            attachButtons(message, Map.of(
//                    "Сплести маскувальну сітку (+15 монет)", "level_1_task",
//                    "Зібрати кошти патріотичними піснями (+15 монет)", "level_1_task",
//                    "Вступити в Міністерство Мемів України (+15 монет)", "level_1_task"

                    // 4.3)
                    buttons.get(0), "level_1_task",
                    buttons.get(1), "level_1_task",
                    buttons.get(2), "level_1_task"
            ));

            sendApiMethodAsync(message);
        }

        // 2-3), 3) Обработчик нажатия кнопки в чате ТГ
        if(update.hasCallbackQuery()) {
              // 2)
//            if(update.getCallbackQuery().getData().equals("glory_for_ukraine")) {
//                SendMessage message = createMessage("Героям Слава!");
//                message.setChatId(chatId);
//
//                // DZ2-1
//                attachButtons(message, Map.of(
//                        "Слава нації!", "glory_for_nation"
//                ));
//
//                sendApiMethodAsync(message);
//            }

            // 3) (level 2)
            if(update.getCallbackQuery().getData().equals("level_1_task") && getLevel(chatId) == 1) {
                // increase level (not to get level 2 again after level 1 already clicked
                setLevel(chatId, 2);

                sendImage("level-2", chatId);

                SendMessage message = createMessage("*Вітаємо на другому рівні! Твій гусак - біогусак.*\n" +
                        "Баланс: 20 монет. \n" +
                        "Обери завдання, щоб перейти на наступний рівень");
                message.setChatId(chatId);

                // 4.4) + random
                List<String> buttons = Arrays.asList(
                        "Зібрати комарів для нової біологічної зброї (+15 монет)",
                        "Пройти курс молодого бійця (+15 монет)",
                        "Задонатити на ЗСУ (+15 монет)",
                        "Збити дрона банкою огірків (+15 монет)",
                        "Зробити запаси коктейлів Молотова (+15 монет)"
                );
                buttons = getRandom3(buttons);

                attachButtons(message, Map.of(
//                        "Зібрати комарів для нової біологічної зброї (+15 монет)", "level_2_task",
//                        "Пройти курс молодого бійця (+15 монет)", "level_2_task",
//                        "Задонатити на ЗСУ (+15 монет)", "level_2_task"

                        // 4.5)
                        buttons.get(0), "level_2_task",
                        buttons.get(1), "level_2_task",
                        buttons.get(2), "level_2_task"
                ));
                sendApiMethodAsync(message);
            }
        }

        // DZ2-2
//        if(update.hasCallbackQuery()) {
//            if (update.getCallbackQuery().getData().equals("glory_for_nation")) {
//                SendMessage message = createMessage("Смерть ворогам!");
//                message.setChatId(chatId);
//
//                sendApiMethodAsync(message);
//            }
//        }

        // DZ 3
        if(update.hasCallbackQuery()) {
            if(update.getCallbackQuery().getData().equals("level_2_task") && getLevel(chatId) == 2) {
                System.out.println("Level 3");

                setLevel(chatId, 3);

                sendImage("level-3", chatId);

                SendMessage message = createMessage("*Вітаємо на третьому рівні! Твій гусак - бандеростажер.*\n" +
                        "Баланс: 35 монет. \n" +
                        "Обери завдання, щоб перейти на наступний рівень");
                message.setChatId(chatId);

                // 4.6) + random
                List<String> buttons = Arrays.asList(
                        "Злітати на тестовий рейд по чотирьох позиціях (+15 монет)",
                        "Відвезти гуманітарку на передок (+15 монет)",
                        "Знайти зрадника та здати в СБУ (+15 монет)",
                        "Навести арту на орків (+15 монет)",
                        "Притягнути танк трактором (+15 монет)"
                );
                buttons = getRandom3(buttons);

                attachButtons(message, Map.of(
//                        "Злітати на тестовий рейд по чотирьох позиціях (+15 монет)", "level_3_task",
//                        "Відвезти гуманітарку на передок (+15 монет)", "level_3_task",
//                        "Знайти зрадника та здати в СБУ (+15 монет)", "level_3_task",
//                        "Навести арту на орків (+15 монет)", "level_3_task",
//                        "Притягнути танк трактором (+15 монет)", "level_3_task"
                        // 4.5)
                        buttons.get(0), "level_3_task",
                        buttons.get(1), "level_3_task",
                        buttons.get(2), "level_3_task"
                ));
                sendApiMethodAsync(message);
            }
        }

        // DZ 4
        if(update.hasCallbackQuery()) {
            if(update.getCallbackQuery().getData().equals("level_3_task") && getLevel(chatId) == 3) {
                System.out.println("Level 4");

                setLevel(chatId, 4);

                sendImage("level-4", chatId);

                SendMessage message = createMessage("*Вітаємо на останньому рівні! Твій гусак - готова біологічна зброя - бандерогусак.*\n" +
                        "Баланс: 50 монет. \n" +
                        "Тепер ти можеш придбати Джавелін і глушити чмонь");
                message.setChatId(chatId);

                attachButtons(message, Map.of("Купити Джавелін (50 монет)", "level_4_task"));

                sendApiMethodAsync(message);
            }
        }

        if(update.hasCallbackQuery()) {
            if(update.getCallbackQuery().getData().equals("level_4_task") && getLevel(chatId) == 4) {
                System.out.println("Level 5");

                setLevel(chatId, 5);

                SendMessage message = createMessage("*Джавелін твій. Повний вперед!*");
                message.setChatId(chatId);

                sendImage("final", chatId);

                sendApiMethodAsync(message);
            }
        }
    }

    public Long getChatId(Update update) {
        // Если отправлен текст
        if(update.hasMessage()) {
            return update.getMessage().getFrom().getId();
        }

        // Если нажата кнопка
        if(update.hasCallbackQuery()) {
            return update.getCallbackQuery().getFrom().getId();
        }

        return null;
    }

    public SendMessage createMessage(String text) {
        SendMessage message = new SendMessage();

        message.setText(text);
        // to UTF-8
        //message.setText(new String(text.getBytes(), StandardCharsets.UTF_8));
        message.setParseMode("markdown");

        return message;
    }

    // 2) Button - btn (видимая часть, идентификатор || payload)
    public void attachButtons(SendMessage message, Map<String, String> buttons) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        // Rows, Cols (2d matrix of buttons)
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        for(String buttonName : buttons.keySet()) {
            String buttonValue = buttons.get(buttonName);

            InlineKeyboardButton button = new InlineKeyboardButton();

            button.setText(buttonName);
            // to UTF-8 (for TG)
            //button.setText(new String(buttonName.getBytes(), StandardCharsets.UTF_8));
            button.setCallbackData(buttonValue);

            keyboard.add(Arrays.asList(button));
        }
        markup.setKeyboard(keyboard);
        message.setReplyMarkup(markup);
    }

    // 3) Images, animation
    public void sendImage(String name, Long chatId) {
        SendAnimation animation = new SendAnimation();

        InputFile inputFile = new InputFile();
        inputFile.setMedia(new File("images/" + name + ".gif"));

        animation.setAnimation(inputFile);
        animation.setChatId(chatId);

        executeAsync(animation);
    }
    public int getLevel(Long chatId) {
        return levels.getOrDefault(chatId, 1);
    }
    public void setLevel(Long chatId, int level) {
        levels.put(chatId, level);
    }

    // 4.1
    public List<String> getRandom3(List<String> variants) {
        ArrayList<String> copy = new ArrayList<>(variants);
        Collections.shuffle(copy);

        return copy.subList(0,3);                   // first 3 elements
    }
}



/*
@asv_2_bot

* BONUS
https://github.com/Nazik007/TelegramBot
Обіцяний бонус за всі чотири виконаних ДЗ 🎁
Підготували для тебе практичний проєкт від нашого студента, який допоможе тобі ще більше попрактикуватися у джаві💪
Що може робити цей бот? Все просто: він бере дані в банку про обмін валют і пересилає користувачу.
Відповідно до збережених налаштувань користувача, наприклад вибір банку, валюти і часу сповіщення
Як відкрити код файлу:
1. Переходимо за посиланням
2. Відкриваємо папку ""src/main/java""
3. Завантажуємо та інсталюємо у програмі
4. Вносимо зміни та дивимось результат

UML Diagrams:
Files/Settings/Plugins/Diagram

1) https://www.youtube.com/watch?v=SI5lX0ktvpg
-2) IJ Community
-1) JDK 11
0) New project
Build System: Gradle
Lang: Java
JDK: 11
Gradle DSL: (Kotlin)
https://mvnrepository.com/artifact/org.telegram/telegrambots/6.3.0

	+build.gradle (check, wait for install), dependencies
    		// https://mvnrepository.com/artifact/org.telegram/telegrambots
    		implementation("org.telegram:telegrambots:6.3.0")

		[Load Gradle Changes] - Обновить проект (установить зависимости)	// Download https://repo.maven.apache.org...

	|>	public static void main() {						// RUN PROGRAM! (Hello world)

TG	>>botfather
/start
/newbot
>>NameBot
>>User Name (NameBot)
>link
>API Token
    // User name: @ASV_2_Bot
    // API Token: 6094929465:AAFZMTflwJuPNpu89LE7K85XY_t47MhdC-0

1) Telegram bot creation/registration
public class Main extends TelegramLongPollingBot {					// <Alt+Enter>: Implements methods: getBotUsername(), getBotToken(), onUpdateReceived()
   public static void main(String[] args) throws TelegramApiException {
	TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);		// <Alt+Enter>: Add exception to method signature
        api.registerBot(new Main());

### Coding error:
��-��-��!
³���� � ��� ����������� ������� ��� �������.
�� ������� ������ �71

З Вашим кодом все гаразд, це проблема кодування на комп'ютері. Скористайтесь інструкціює щоб це налаштувати 🤗
"1)додайте в кінець файлу build.gradle ось цей фрагмент коду

compileJava.options.encoding = 'UTF-8'
tasks.withType(JavaCompile) {options.encoding = 'UTF-8'}

2)в методі attachButtons змініть
button.setText(new String(buttonName.getBytes(), StandardCharsets.UTF_8)); на button.setText(buttonName);

3)а в методі createMessage змініть
message.setText(new String(text.getBytes(), StandardCharsets.UTF_8)); на message.setText(text);"

2)  @Override
    public String getBotUsername() {
        return "@ASV_2_Bot";
    }

    @Override
    public String getBotToken() {
        return "6094929465:AAFZMTflwJuPNpu89LE7K85XY_t47MhdC-0";
    }

    @Override
    public void onUpdateReceived(Update update) {               // Взаимодействие с ботом (onUpdateReceived()) - реакция на события
        // Отправить Hello на любое действие
        Long chatId = getChatId(update);

        a) get chatId
        update.getMessage().getFrom().getId();                  // Если пришло текстовое сообщение из апдейта
        update.getCallbackQuery().getFrom().getId();            // Если апдейт - нажата кнопка
        ->public Long getChatId(Update update) {
            if(update.hasMessage()) {
                return update.getMessage().getFrom().getId();
            }
            if(update.hasCallbackQuery()) {
                return update.getCallbackQuery().getFrom().getId();
            }
        
        b) Отправить текст пользователю -> public SendMessage createMessage(String text) {
            SendMessage msg = createMessage("*Hello* Sergii!");
            msg.setText(text);
            msg.setParseMode("markdown");
            return msg;

            //msg.setChatId(chatId);
            //sendApiMethodAsync(msg);          // Отправка сообщения в ответ на взаимодействие с ботом

>>TRY TO START

2) https://www.youtube.com/watch?v=9H1YH8cPUEA
Cyrrilic: msg.setText(new String(text.getBytes(), StandardCharsets.UTF_8));
          button.setText(new String(buttonName.getBytes(), StandardCharsets.UTF_8));
TG: UTF-8
Java: UTF-16
Каркас(кнопки, картинки)

*[+Эванс Б., Вербург М. - Java. Новое поколение разработки - 2014] СОВЕТ
Не забывайте, что при работе со строками String всегда требуется знать их кодировку. Если вы
забудете указать кодировку (это делается с помощью класса StandardCharsets, например, так:
new String(byte[], StandardCharsets .UTF_8)), то позже могут возникнуть неожиданные проблемы,
связанные именно с ней.

#12 Чтобы сборка была
согласованной на любых платформах, вы также указываете исходную кодировку
<sourceEncoding>, задавая для нее значение UTF�8
pom.xml
...
<properties>
    <project.build.sourceEncoding>
        UTF-8                                       // Платформонезависимая кодировка!!!
    </project.build.sourceEncoding>
</properties>

В сравнительно старых проектах на Java обычно использовался сбо�
рочный инструмент Ant, в более новых применяется Maven или Gradle.
Gradle — замечательный новичок в ряду сборочных инструментов. В нем применяется подход, противоположный
избранному в Maven. В Gradle вы освободитесь от жестких ограничений и сможете выстраивать сборку по своему
усмотрению. Как и в Maven, в Gradle предоставляется управление зависимостями и множество других возможностей. Если вы хотите попробовать Gradle на практике, посетите сайт www.gradle.org, где подробно рассказано об
этом инструменте.
Поскольку мы хотим научить вас качественным приемам организации сборок, то все же полагаем, что для
достижения нашей цели лучше всего подходит Maven. При работе с ним вы должны придерживаться строго
организованного сборочного цикла, а также можете запускать сборки любых проектов Maven, подготовленных
где и когда угодно.

[5.3.1. Знакомство с javap]
По умолчанию javap отображает методы с уровнем видимости public, protected
и — доступ по умолчанию — защищенные на уровне пакета методы. При примене�
нии переключателя -p вы также увидите закрытые (private) методы и поля.
C:\Users\Сергей В10\IdeaProjects\TG_Bot-2\build\classes\java\main\org\example>javap Main.class
Compiled from "Main.java"
public class org.example.Main extends org.telegram.telegrambots.bots.TelegramLongPollingBot {
  public org.example.Main();
  public static void main(java.lang.String[]) throws org.telegram.telegrambots.meta.exceptions.TelegramApiException;
  public java.lang.String getBotUsername();
  public java.lang.String getBotToken();
  public void onUpdateReceived(org.telegram.telegrambots.meta.api.objects.Update);
  public java.lang.Long getChatId(org.telegram.telegrambots.meta.api.objects.Update);
  public org.telegram.telegrambots.meta.api.methods.send.SendMessage createMessage(java.lang.String);
  public void attachButtons(org.telegram.telegrambots.meta.api.methods.send.SendMessage, java.util.Map<java.lang.String, java.lang.String>);
  public void sendImage(java.lang.String, java.lang.Long);
  public int getLevel(java.lang.Long);
  public void setLevel(java.lang.Long, int);
  public java.util.List<java.lang.String> getRandom3(java.util.List<java.lang.String>);
}

C:\Users\Сергей В10\IdeaProjects\TG_Bot-2\build\classes\java\main\org\example>javap -p Main.class
Compiled from "Main.java"
public class org.example.Main extends org.telegram.telegrambots.bots.TelegramLongPollingBot {
  private java.util.Map<java.lang.Long, java.lang.Integer> levels;
  public org.example.Main();
  public static void main(java.lang.String[]) throws org.telegram.telegrambots.meta.exceptions.TelegramApiException;
  public java.lang.String getBotUsername();
  public java.lang.String getBotToken();
  public void onUpdateReceived(org.telegram.telegrambots.meta.api.objects.Update);
  public java.lang.Long getChatId(org.telegram.telegrambots.meta.api.objects.Update);
  public org.telegram.telegrambots.meta.api.methods.send.SendMessage createMessage(java.lang.String);
  public void attachButtons(org.telegram.telegrambots.meta.api.methods.send.SendMessage, java.util.Map<java.lang.String, java.lang.String>);
  public void sendImage(java.lang.String, java.lang.Long);
  public int getLevel(java.lang.Long);
  public void setLevel(java.lang.Long, int);
  public java.util.List<java.lang.String> getRandom3(java.util.List<java.lang.String>);
}

javap предоставляет полезный переключатель -s, выводящий дескрипторы типов
сигнатур для некоторых методов, рассмотренных нами выше:
C:\Users\Сергей В10\IdeaProjects\TG_Bot-2\build\classes\java\main\org\example>javap -p -s Main.class
Compiled from "Main.java"
public class org.example.Main extends org.telegram.telegrambots.bots.TelegramLongPollingBot {
  private java.util.Map<java.lang.Long, java.lang.Integer> levels;
    descriptor: Ljava/util/Map;
  public org.example.Main();
    descriptor: ()V

  public static void main(java.lang.String[]) throws org.telegram.telegrambots.meta.exceptions.TelegramApiException;
    descriptor: ([Ljava/lang/String;)V

  public java.lang.String getBotUsername();
    descriptor: ()Ljava/lang/String;

  public java.lang.String getBotToken();
    descriptor: ()Ljava/lang/String;

  public void onUpdateReceived(org.telegram.telegrambots.meta.api.objects.Update);
    descriptor: (Lorg/telegram/telegrambots/meta/api/objects/Update;)V

  public java.lang.Long getChatId(org.telegram.telegrambots.meta.api.objects.Update);
    descriptor: (Lorg/telegram/telegrambots/meta/api/objects/Update;)Ljava/lang/Long;

  public org.telegram.telegrambots.meta.api.methods.send.SendMessage createMessage(java.lang.String);
    descriptor: (Ljava/lang/String;)Lorg/telegram/telegrambots/meta/api/methods/send/SendMessage;

  public void attachButtons(org.telegram.telegrambots.meta.api.methods.send.SendMessage, java.util.Map<java.lang.String, java.lang.String>);
    descriptor: (Lorg/telegram/telegrambots/meta/api/methods/send/SendMessage;Ljava/util/Map;)V

  public void sendImage(java.lang.String, java.lang.Long);
    descriptor: (Ljava/lang/String;Ljava/lang/Long;)V

  public int getLevel(java.lang.Long);
    descriptor: (Ljava/lang/Long;)I

  public void setLevel(java.lang.Long, int);
    descriptor: (Ljava/lang/Long;I)V

  public java.util.List<java.lang.String> getRandom3(java.util.List<java.lang.String>);
    descriptor: (Ljava/util/List;)Ljava/util/List;
}

Чтобы просмотреть информацию в пуле констант, можно использовать javap -v.
...

При использовании javap с ключом -c можно дизассемблировать классы.
Наша основная цель — исследовать байт�код, который содержится внутри
методов. Кроме того, мы будем использовать ключ -p — так мы увидим и тот байт�
код, который находится в закрытых методах.


// 2-2)
        if(update.hasMessage() && update.getMessage().getText().equals("/start")) { ...

3) https://www.youtube.com/watch?v=vdBW-lM-vzM      (DZ +3 level)
3a) +/images
+public void sendImage(String name, Long chatId) {
    SendAnimation
}

3c) *public void onUpdateReceived(Update update) {
    ...
    if(update.hasMessage() && update.getMessage().getText().equals("/start")) {
        //

        // 3) (Image, message, buttons, sendApiMethodAsync(message), if(update.hasCallbackQuery()) {...})
        sendImage("level-1", chatId);

        SendMessage message = createMessage("Ґа-ґа-ґа!\n" + ...);
        message.setChatId(chatId);

        attachButtons(message, Map.of(
               "Сплести маскувальну сітку (+15 монет)", "level_1_task",
               "Зібрати кошти патріотичними піснями (+15 монет)", "level_1_task",
               "Вступити в Міністерство Мемів України (+15 монет)", "level_1_task"
        ));
        sendApiMethodAsync(message);

        if(update.hasCallbackQuery()) {
              // 2)
//            if(update.getCallbackQuery().getData().equals("glory_for_ukraine")) {
//                SendMessage message = createMessage("Героям Слава!");
//                message.setChatId(chatId);
//
//                // DZ2-1
//                attachButtons(message, Map.of(
//                        "Слава нації!", "glory_for_nation"
//                ));
//
//                sendApiMethodAsync(message);
//            }

            // 3) (level 2)
            if(update.getCallbackQuery().getData().equals("level_1_task") && getLevel(chatId) == 1) {
                // increase level (not to get level 2 again after level 1 already clicked
                setLevel(chatId, 2);


                sendImage("level-2", chatId);
}

>> /start
> Image (animation)

3b) +private Map<Long, Integer> levels = new HashMap<>();\
+ public int getLevel(Long chatId) {
    return levels.getOrDefault(chatId, 1);
 }
 +public void setLevel(Long chatId, int level) {
    levels.put(chatId, level);
 }

4) https://www.youtube.com/watch?v=_N1ZJeGoiGw  (DZ-4 random 3 messages from 5)
+ public List<String> getRandom3(List<String> variants) {
}
+List<String> buttons = Arrays.asList(
    "msg1",
    ...
)
buttons = getRandom3(buttons);

* attachButtons(message, Map.of(
//                    "Сплести маскувальну сітку (+15 монет)", "level_1_task",
//                    "Зібрати кошти патріотичними піснями (+15 монет)", "level_1_task",
//                    "Вступити в Міністерство Мемів України (+15 монет)", "level_1_task"

                    // 4.3)
                    buttons.get(0), "level_1_task",
                    ...




TG: UTF-8
Java: UTF-16 (default)

 */