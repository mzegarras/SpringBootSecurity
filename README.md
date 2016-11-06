# Spring Boot OAuth 2 Examples



La idea original la encuentran en:

http://www.hascode.com/2016/03/setting-up-an-oauth2-authorization-server-and-resource-provider-with-spring-boot/


## Running the Identity Server

Using Maven

```
cd identity-server && mvn spring-boot:run
```

## Running the Resource Provider

Using Maven

```
cd resource-provider && mvn spring-boot:run
```

## Requesting a Token

Using Curl

```
   curl -XPOST -k -vi foo:foosecret@localhost:9000/hascode/oauth/token \
   -d grant_type=password -d client_id=foo -d client_secret=abc123 \
   -d redirect_uri=http://www.hascode.com -d username=manu -d password=123456
```

```
   curl -XPOST -k -vi foo:foosecret@localhost:9000/hascode/oauth/token \
   -d grant_type=password -d client_id=foo -d client_secret=abc123 \
   -d redirect_uri=http://www.hascode.com -d username=niki -d password=123456
```

# Accessing the secured Resource

```
   TOKEN = 'xxxxxxx'
   curl -vi -H "Authorization: Bearer $TOKEN" http://localhost:9001/resource/
```

