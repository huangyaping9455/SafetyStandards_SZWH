INSERT INTO `blade_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1589260567244345346', 1123598815738675201, 'vehiclebiangengjilu', '', 'menu', '/anbiao/vehiclebiangengjilu', NULL, 1, 1, 0, 1, NULL, 0);
INSERT INTO `blade_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1589260567244345347', '1589260567244345346', 'vehiclebiangengjilu_add', '新增', 'add', '/anbiao/vehiclebiangengjilu/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `blade_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1589260567244345348', '1589260567244345346', 'vehiclebiangengjilu_edit', '修改', 'edit', '/anbiao/vehiclebiangengjilu/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `blade_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1589260567244345349', '1589260567244345346', 'vehiclebiangengjilu_delete', '删除', 'delete', '/api/blade-anbiao/vehiclebiangengjilu/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `blade_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1589260567244345350', '1589260567244345346', 'vehiclebiangengjilu_view', '查看', 'view', '/anbiao/vehiclebiangengjilu/view', 'file-text', 4, 2, 2, 1, NULL, 0);
