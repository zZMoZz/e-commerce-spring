## Database Setup

### Files
- ERD Diagram: [ecommerce_erd.png](./database/ecommerce_erd.png)
- PostgreSQL Schema: [ecommerce_schema.sql](./database/ecommerce_schema.sql)
- Insert Production-Like Data: [ecommerce_fake_data.sql](./database/ecommerce_fake_data.sql)

### Run with Docker
Run this command from the `/database` folder (where `.sql` files are located).

```bash
docker run --name ecommerce-postgres \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -e POSTGRES_DB=ecommerce_db \
  -p 5432:5432 \
  -v $(pwd)/ecommerce_schema.sql:/docker-entrypoint-initdb.d/1_schema.sql \
  -v $(pwd)/ecommerce_fake_data.sql:/docker-entrypoint-initdb.d/2_fake_data.sql \
  -d postgres:latest
```