CREATE TYPE gender_type AS ENUM ('male', 'female');
CREATE TABLE customer (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(100) UNIQUE NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone_number VARCHAR(20) UNIQUE,
    gender gender_type,
    email_verified_at TIMESTAMPTZ,
    last_login_at TIMESTAMPTZ,
    is_active BOOLEAN NOT NULL DEFAULT FALSE,
    slug VARCHAR(150) UNIQUE NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ
);

CREATE TABLE customer_address (
    id BIGSERIAL PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    phone VARCHAR(20) NOT NULL CHECK (phone ~ '^[0-9]+$'),
    phone_backup VARCHAR(11) CHECK (phone_backup ~ '^[0-9]+$'),
    country VARCHAR(100) NOT NULL,
    city VARCHAR(100) NOT NULL,
    street VARCHAR(255) NOT NULL, 
    building_name VARCHAR(100),
    location POINT,
    is_default BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ,
    CONSTRAINT fk_customer_address_customer FOREIGN KEY (customer_id) REFERENCES customer (id) ON DELETE CASCADE
);

CREATE TABLE brand (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    description TEXT,
    icon_url VARCHAR(500) UNIQUE,
    is_active BOOLEAN NOT NULL DEFAULT FALSE,
    slug VARCHAR(50) UNIQUE NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ
);

CREATE EXTENSION IF NOT EXISTS ltree;
CREATE TABLE category (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    description TEXT,
    icon_url VARCHAR(500) UNIQUE,
    display_order INTEGER NOT NULL DEFAULT 0 CHECK (display_order >= 0),
    parent_path LTREE UNIQUE NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT FALSE,
    slug VARCHAR(50) UNIQUE NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ
);

CREATE TYPE category_property_datatype AS ENUM ('string', 'integer', 'decimal', 'boolean');
CREATE TABLE category_property (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL,
    value_datatype category_property_datatype NOT NULL DEFAULT 'string',
    is_active BOOLEAN NOT NULL DEFAULT FALSE,
    slug VARCHAR(100) UNIQUE NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ
);

CREATE TABLE category_properties (
    id SERIAL PRIMARY KEY, 
    property_id INTEGER NOT NULL,
    category_id INTEGER NOT NULL,
    unit VARCHAR(50),
    is_variant BOOLEAN NOT NULL DEFAULT FALSE,
    is_filterable BOOLEAN NOT NULL DEFAULT FALSE,
    display_order SMALLINT NOT NULL DEFAULT 0 CHECK (display_order >= 0),
    is_active BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_category_properties_category_property FOREIGN KEY (property_id) REFERENCES category_property (id) ON DELETE CASCADE,
    CONSTRAINT fk_category_properties_category FOREIGN KEY (category_id) REFERENCES category (id) ON DELETE CASCADE,
    UNIQUE (category_id, property_id)
);

CREATE TABLE category_property_value (
    id SERIAL PRIMARY KEY,
    value VARCHAR(255) NOT NULL,
    category_properties_id INTEGER NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ,
    CONSTRAINT fk_category_property_value_category_properties FOREIGN KEY (category_properties_id) REFERENCES category_properties (id) ON DELETE CASCADE,
    UNIQUE (value, category_properties_id)
);

CREATE TABLE product (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(150) UNIQUE NOT NULL,
    description TEXT,
    brand_id INTEGER,
    category_id INTEGER NOT NULL,
    average_rating NUMERIC(2, 1) NOT NULL DEFAULT 0.0 CHECK (average_rating BETWEEN 0.0 AND 5.0),
    total_reviews INTEGER NOT NULL DEFAULT 0 CHECK (total_reviews >= 0),
    is_active BOOLEAN NOT NULL DEFAULT FALSE,
    slug VARCHAR(150) UNIQUE NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ,
    CONSTRAINT fk_product_category FOREIGN KEY (category_id) REFERENCES category (id),
    CONSTRAINT fk_product_brand FOREIGN KEY (brand_id) REFERENCES brand (id) ON DELETE SET NULL
);

CREATE TABLE product_variant (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL,
    price NUMERIC(10,2) NOT NULL CHECK (price >= 0),
    quantity INTEGER NOT NULL DEFAULT 0 CHECK (quantity >= 0),
    is_active BOOLEAN NOT NULL DEFAULT FALSE,
    sku VARCHAR(255) UNIQUE NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ,
    CONSTRAINT fk_product_variant_product FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE
);

CREATE TABLE product_specification (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL,
    category_properties_id INTEGER,
    value_id INTEGER,
    value_text VARCHAR(255),
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ,
    CONSTRAINT fk_product_specification_product FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE,
    CONSTRAINT fk_product_specification_category_properties FOREIGN KEY (category_properties_id) REFERENCES category_properties (id) ON DELETE SET NULL,
    CONSTRAINT fk_product_specification_category_property_value FOREIGN KEY (value_id) REFERENCES category_property_value (id),
    CHECK ((value_text IS NOT NULL) OR (category_properties_id IS NOT NULL AND value_id IS NOT NULL))
);

CREATE TABLE product_variant_attribute (
    id BIGSERIAL PRIMARY KEY,
    variant_id BIGINT NOT NULL,
    category_properties_id INTEGER,
    value_id INTEGER,
    value_text VARCHAR(255),
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ,
    CONSTRAINT fk_product_variant_attribute_product FOREIGN KEY (variant_id) REFERENCES product_variant (id) ON DELETE CASCADE,
    CONSTRAINT fk_product_variant_attribute_category_properties FOREIGN KEY (category_properties_id) REFERENCES category_properties (id) ON DELETE SET NULL,
    CONSTRAINT fk_product_variant_attribute_category_property_value FOREIGN KEY (value_id) REFERENCES category_property_value (id),
    UNIQUE (variant_id, category_properties_id),
    CHECK ((value_text IS NOT NULL) OR (category_properties_id IS NOT NULL AND value_id IS NOT NULL))
);

CREATE TABLE product_image (
    id BIGSERIAL PRIMARY KEY,
    url VARCHAR(500) UNIQUE NOT NULL,
    product_id BIGINT NOT NULL,
    property_value_id INTEGER,
    is_thumbnail BOOLEAN NOT NULL DEFAULT FALSE,
    display_order SMALLINT NOT NULL DEFAULT 0,
    alt_text VARCHAR(255),
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ,
    CONSTRAINT fk_product_image_product FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE,
    CONSTRAINT fk_product_image_category_property_value FOREIGN KEY (property_value_id) REFERENCES category_property_value  (id) ON DELETE CASCADE
);

CREATE TABLE product_review (
    id BIGSERIAL PRIMARY KEY,
    rating NUMERIC(2, 1) NOT NULL CHECK (rating BETWEEN 0.0 AND 5.0),
    title VARCHAR(255),
    comment TEXT,
    verified_purchase BOOLEAN NOT NULL DEFAULT FALSE,
    likes_count INTEGER NOT NULL DEFAULT 0 CHECK (likes_count >= 0),
    customer_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ,
    CONSTRAINT fk_product_review_customer FOREIGN KEY (customer_id) REFERENCES customer (id),
    CONSTRAINT fk_product_review_product FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE,
    CONSTRAINT uq_customer_review UNIQUE (customer_id, product_id)
);

CREATE TABLE product_tag (
    id SERIAL PRIMARY KEY,
    name VARCHAR(150) UNIQUE NOT NULL,
    icon_url VARCHAR(500) UNIQUE,
    display_order SMALLINT NOT NULL DEFAULT 0 CHECK (display_order >= 0),
    is_active BOOLEAN NOT NULL DEFAULT FALSE,
    slug VARCHAR(150) UNIQUE NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ
);

CREATE TABLE product_tag_link (
    product_id BIGINT NOT NULL,
    tag_id INTEGER NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ,
    CONSTRAINT fk_product_tag_link_product FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE,
    CONSTRAINT fk_product_tag_link_product_tag FOREIGN KEY (tag_id) REFERENCES product_tag (id) ON DELETE CASCADE,
    CONSTRAINT pk_product_tag_link PRIMARY KEY (product_id, tag_id)
);

CREATE TABLE cart (
    id BIGSERIAL PRIMARY KEY,
    customer_id BIGINT UNIQUE NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ,
    CONSTRAINT fk_cart_customer FOREIGN KEY (customer_id) REFERENCES customer (id) ON DELETE CASCADE
);

CREATE TABLE cart_item (
    cart_id BIGINT NOT NULL,
    variant_id BIGINT NOT NULL,
    quantity INTEGER NOT NULL CHECK (quantity > 0) DEFAULT 1,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ,
    CONSTRAINT fk_cart_item_cart FOREIGN KEY (cart_id) REFERENCES cart (id) ON DELETE CASCADE,
    CONSTRAINT fk_cart_item_product_variant FOREIGN KEY (variant_id) REFERENCES product_variant (id) ON DELETE CASCADE,
    CONSTRAINT pk_cart_item PRIMARY KEY (cart_id, variant_id)
);

CREATE TABLE wishlist (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    customer_id BIGINT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ,
    CONSTRAINT fk_wishlist_customer FOREIGN KEY (customer_id) REFERENCES customer (id) ON DELETE CASCADE
);

CREATE TABLE wishlist_item (
    wishlist_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    CONSTRAINT fk_wishlist_item_wishlist FOREIGN KEY (wishlist_id) REFERENCES wishlist (id) ON DELETE CASCADE,
    CONSTRAINT fk_wishlist_item_product FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE,
    CONSTRAINT pk_wishlist_item PRIMARY KEY (wishlist_id, product_id)
);

CREATE TYPE discount_type AS ENUM ('amount', 'percent');
CREATE TABLE coupon (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(100) UNIQUE NOT NULL,
    description TEXT,
    discount_type discount_type NOT NULL DEFAULT 'amount',
    discount_value DECIMAL(10, 2) NOT NULL CHECK (discount_value >= 0),
    max_discount_value DECIMAL(10, 2) CHECK (max_discount_value >= 0),
    min_order_total_price DECIMAL(10, 2) CHECK (min_order_total_price >= 0),
    usage_limit INTEGER CHECK (usage_limit >= 0),
    usage_count INTEGER DEFAULT 0 CHECK (usage_count >= 0),
    usage_per_customer_limit SMALLINT CHECK (usage_per_customer_limit >= 0),
    valid_from TIMESTAMPTZ,
    valid_until TIMESTAMPTZ,
    is_active BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ,
    CHECK (usage_limit IS NULL OR usage_limit >= usage_count)
);

CREATE TABLE coupon_usage (
    coupon_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    usage_count SMALLINT NOT NULL DEFAULT 1 CHECK (usage_count >= 0),
    latest_time TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_coupon_usage_coupon FOREIGN KEY (coupon_id) REFERENCES coupon (id) ON DELETE CASCADE,
    CONSTRAINT fk_coupon_usage_customer FOREIGN KEY (customer_id) REFERENCES customer (id) ON DELETE CASCADE,
    CONSTRAINT pk_coupon_usage PRIMARY KEY (coupon_id, customer_id)
);

CREATE TABLE shipping_partner (
    id SMALLINT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(11) NOT NULL CHECK (phone ~ '^[0-9]+$'),
    website VARCHAR(255) NOT NULL UNIQUE,
    is_active BOOLEAN NOT NULL DEFAULT FALSE,
    is_default BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ
);

CREATE TABLE order_address_snapshot (
    id BIGSERIAL PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    phone VARCHAR(20) NOT NULL CHECK (phone ~ '^[0-9]+$'),
    phone_backup VARCHAR(11) CHECK (phone_backup ~ '^[0-9]+$'),
    country VARCHAR(100) NOT NULL,
    city VARCHAR(100) NOT NULL,
    street VARCHAR(255) NOT NULL, 
    building_name VARCHAR(100),
    location POINT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TYPE privacy_level AS ENUM ('customer', 'moderator', 'admin');
CREATE TABLE order_status (
    id SMALLSERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    color VARCHAR(50) UNIQUE NOT NULL,
    description TEXT,
    privacy privacy_level NOT NULL DEFAULT 'moderator',
    status_order SMALLINT UNIQUE NOT NULL CHECK (status_order >= 0), 
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ
);

CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    order_number VARCHAR(50) UNIQUE NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL CHECK (total_price >= 0),
    status_id INTEGER NOT NULL,
    customer_id BIGINT NOT NULL,
    address_id BIGINT NOT NULL,
    coupon_id BIGINT,
    shipping_partner_id INTEGER NOT NULL,
    tracking_number VARCHAR(100),
    shipping_fee DECIMAL(10, 2) DEFAULT 0 CHECK (shipping_fee >= 0),
    estimated_delivery_at TIMESTAMPTZ,
    order_approved_at TIMESTAMPTZ,
    order_shipped_at TIMESTAMPTZ,
    order_delivered_at TIMESTAMPTZ,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ,
    CONSTRAINT fk_order_customer FOREIGN KEY (customer_id) REFERENCES customer (id),
    CONSTRAINT fk_order_status FOREIGN KEY (status_id) REFERENCES order_status (id),
    CONSTRAINT fk_order_address FOREIGN KEY (address_id) REFERENCES order_address_snapshot (id),
    CONSTRAINT fk_order_shipping_company FOREIGN KEY (shipping_partner_id) REFERENCES shipping_partner (id),
    CONSTRAINT fk_order_coupon FOREIGN KEY (coupon_id) REFERENCES coupon (id)
);

CREATE TABLE order_item (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL,
    variant_id BIGINT NOT NULL,
    quantity INTEGER NOT NULL CHECK (quantity > 0) DEFAULT 1,
    unit_price DECIMAL(10, 2) NOT NULL CHECK (unit_price >= 0),
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ,
    CONSTRAINT fk_order_item_order FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE,
    CONSTRAINT fk_order_item_product_variant FOREIGN KEY (variant_id) REFERENCES product_variant (id),
    UNIQUE (order_id, variant_id)
);

CREATE TABLE order_return_status (
    id SMALLSERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    color VARCHAR(50) UNIQUE NOT NULL,
    description TEXT,
    privacy privacy_level NOT NULL DEFAULT 'moderator',
    status_order SMALLINT UNIQUE NOT NULL CHECK (status_order >= 0), 
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ
);

CREATE TABLE order_return (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL,
    order_item_id BIGINT,
    reason TEXT,
    status SMALLINT NOT NULL,
    refund_amount DECIMAL(10, 2) NOT NULL CHECK (refund_amount >= 0),
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ,
    CONSTRAINT fk_order_return_order FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE,
    CONSTRAINT fk_order_return_order_item FOREIGN KEY (order_item_id) REFERENCES order_item (id) ON DELETE CASCADE,
    CONSTRAINT fk_order_return_order_return_status FOREIGN KEY (status) REFERENCES order_return_status (id)
);

CREATE TYPE payment_method AS ENUM ('CARD', 'COD');
CREATE TYPE payment_status AS ENUM ('pending', 'paid', 'failed', 'refunded');
CREATE TABLE payment (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL,
    method payment_method NOT NULL,
    status payment_status NOT NULL DEFAULT 'pending',
    transaction_id VARCHAR(255),
    amount DECIMAL(10, 2) NOT NULL CHECK (amount >= 0),
    paid_at TIMESTAMPTZ,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ,
    CONSTRAINT fk_payment_order FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE,
    CHECK (method = 'COD' OR transaction_id IS NOT NULL)
);

CREATE TABLE refund_transaction (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL,
    return_request_id BIGINT NOT NULL,
    refund_amount DECIMAL(10, 2) NOT NULL CHECK (refund_amount >= 0),
    method payment_method NOT NULL,
    status payment_status NOT NULL DEFAULT 'pending',
    transaction_id VARCHAR(255),
    paid_at TIMESTAMPTZ,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ,
    CONSTRAINT fk_refund_transaction_order FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE,
    CONSTRAINT fk_refund_transaction_order_return FOREIGN KEY (return_request_id) REFERENCES order_return (id) ON DELETE CASCADE
);

-- This table belongs to another database that related to keycloak
CREATE TABLE user_keycloak (
    id UUID PRIMARY KEY,
    username VARCHAR(100) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    roles TEXT[] NOT NULL,
    phone_number VARCHAR(20) UNIQUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ
);