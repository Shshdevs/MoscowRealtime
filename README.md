### Moscow Realtime – гид-квест по Москве

Github Link: ```https://github.com/Shshdevs/MoscowRealtime.git```

Инструкция по сборке (необходим плагин KotlinMultiplatform):
1. Клонировать репозиторий в папку с проектами AndroidStudio
2. Открыть проект в AndroidStudio
3. Создать и подключить проект Firebase с сервисами Messaging(необязательно), Authentication, Firestore
4. В composeApp добавить файл google-services.json от Firestore
5. Создать проект в Supabase с сервисом storage, создать bucket'ы: users, objects, analyzed-images
6. Получить бесплатную лицензию Yandex Mapkit SDK (Можно получить по ссылке ```https://yandex.ru/maps-api/products/mapkit```)
7. В корень проекта в файле local.properties:
``` gradle
WEB_CLIENT_ID="ваш_web_client_id"
SUPABASE_URL="ваш_url_supabase_проекта"
SUPABASE_KEY="ваш_supabase_api_key"
MAPKIT_API_KEY="ваш_yandex_map_kit_api_key"
DEFAULT_PLACEHOLDER_URL="ссылка_на_изображение_заглушку (например из supabase)"
```
8. Выполнить ./gradlew signInReport, полученый sha-ключ указать в настройках проекта firebase
9. Запустить проект из AndroidStudio

# Специфика для iOS (В Xcode)
1. Запустить MoscowRealtime/iosApp
2. В Info.plist необходимо добавить:
```plist
<key>CFBundleURLTypes</key>
	<array>
		<dict>
			<key>CFBundleURLSchemes</key>
			<array>
				<string>ваш_CFBundleURLSchemes</string>
			</array>
		</dict>
	</array>
	<key>GIDClientID</key>
	<string>ваш_GIDClientID</string>
	<key>GIDServerClientID</key>
	<string>ваш_GIDServerClientID</string>

<key>default_placeholder</key>
	<string>ссылка_на_изображение_заглушку (например из supabase)</string>

<key>ymk_api_key</key>
	<string>ваш_yandex_map_kit_api_key</string>

<key>SUPABASE_KEY</key>
	<string>ваш_supabase_api_key</string>

	<key>SUPABASE_URL</key>
	<string>ваш_url_supabase_проекта</string>

<key>UIBackgroundModes</key>
	<array>
		<string>remote-notification</string>
		<string>location</string>
	</array>
	<key>LSApplicationQueriesSchemes</key>
	<array>
		<string>https</string>
		<string>http</string>
	</array>
<key>BGTaskSchedulerPermittedIdentifiers</key>
	<array>
		<string>com.moscowRT.locationUpdate</string>
	</array>
```

3. В iosApp необходимо добавить GoogleServices-Info.plist от Firebase
4. Выполнить pod install в iosApp
5. Закрыть проект и открыть MoscowRealtime.xcworkspace (НЕ MoscowRealtime.xcodeproj!)
6. Запустить проект из Xcode
