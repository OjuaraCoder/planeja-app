# planeja-app

Monorepo do projeto Planeja, com backend em Java Spring Boot e frontend em Angular 21.

## Estrutura

- `planeja-api/` - backend Spring Boot (foco atual)
- `planeja-front/` - frontend Angular 21
- `package.json` - scripts do monorepo e workspaces do npm

## Como executar

### Backend

```bash
mvn -f planeja-api spring-boot:run
```

### Frontend

```bash
npm --prefix planeja-front run start
```

### Script do monorepo

```bash
npm run dev:api
npm run dev:front
```

## Observações

- O foco principal neste momento está no backend Spring.
- O Angular foi inicializado em `planeja-front` com a versão 21 do CLI.
- A raiz do repositório foi configurada como monorepo para facilitar a evolução das duas aplicações em paralelo.
