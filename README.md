# Veteriner Yönetim Sistemi

Veteriner Yönetim Sistemi, veteriner randevularını, doktorları, hayvanları ve müsait tarihleri yönetmek için tasarlanmış bir API'dir. Veterinerler ve klinik personeli için randevuları planlama ve yönetme sürecini kolaylaştırır.

## Özellikler
- **Doktor Yönetimi:** Doktorları ekleyin, güncelleyin ve listeleyin.
- **Hayvan Yönetimi:** Hayvanları ekleyin, güncelleyin ve listeleyin.
- **Müşteri Yönetimi:** Müşterileri ekleyin, güncelleyin ve listeleyin.
- **Aşı Yönetimi:** Hayvanlara yapılan aşıları ekleyin, güncelleyin ve listeleyin.
- **Müsait Tarih Yönetimi:** Doktorların müsaitlik tarihlerini belirleyin ve yönetin.
- **Randevu Yönetimi:** Randevuları planlayın, güncelleyin ve yönetin.
- **Raporlama:** Belirli tarih aralıklarına göre randevuları listeleyin ve raporlayın.
- **Detay Görüntüleme:** Seçilen hayvana ait aşıları ve randevu detaylarını görüntüleyin.

## Kullanılan Teknolojiler
- **Java:** Ana programlama dili.
- **Spring Boot:** Hızlı uygulama geliştirme ve yapılandırma için.
- **Hibernate (JPA):** Veritabanı işlemleri için.
- **PostgreSQL:** Veritabanı yönetim sistemi.
- **ModelMapper:** Nesne-DTO dönüşümleri için.
- **Maven:** Proje yönetimi ve bağımlılık yönetimi için.

## Gereksinimler
- **Java:** 22.0.1 veya üstü
- **Maven:** 3.6.3 veya üstü
- **PostgreSQL:** 12 veya üstü

## Proje Yapısı
- `src/main/java/dev/patika/VeterinaryManagementSystem/` - Ana uygulama ve paket yapısını içerir.
- `business` - Servis arayüzleri ve uygulamalarını içerir.
- `dao` - Depo arayüzlerini içerir.
- `entities` - Varlık sınıflarını içerir.
- `api` - Kontrolcü sınıflarını içerir.
- `dto` - İstek ve yanıt için Veri Transfer Nesnelerini (DTO) içerir.
- `core` - Yardımcı sınıflar, istisnalar ve yapılandırmaları içerir.
